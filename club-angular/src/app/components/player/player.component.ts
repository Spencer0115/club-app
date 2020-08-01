import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import { Player } from 'src/app/entities/player';
import { Event } from 'src/app/entities/event';
import { Team } from 'src/app/entities/team';
import { CommonService } from 'src/app/services/common/common.service';
import { User } from 'src/app/entities/user';
import { Message } from 'src/app/entities/message';
import { setRootDomAdapter } from '@angular/platform-browser/src/dom/dom_adapter';
import { Common } from 'src/app/entities/common';

@Component({
  selector: 'app-player',
  templateUrl: './player.component.html',
  styleUrls: ['./player.component.css']
})
export class PlayerComponent implements OnInit {

  constructor(
    private _location: Location,
    private route:ActivatedRoute,
    private commonService:CommonService
  ) { }
  picSrc = "assets/upload-dir/";

  backClicked() {
    this._location.back();
  }
  player:Player = new Player;
  team:Team =new Team;
  eventList: Event[] = new Array();

  getPlayer(){
    this.commonService.getPlayerById(this.playerId).subscribe(player=>this.player=player);
  }

  getEventList(){
    this.commonService.getEventListByPlayerId(this.playerId,this.sort,this.order).subscribe(eventList=>{this.eventList=eventList;});
  }

  reloadList(sort:string){
    let order = "1";
    if(sort==this.sort){
      if(this.order="1"){
        order = "1";
      }else{
        this.order="-1";
      }
    }
    this.commonService.getEventListByPlayerId(this.playerId,sort,order).subscribe(eventList=>this.eventList=eventList);
  }
 
  editProfile:boolean = false;
  playerId = null;
  user:User = new User;
  messageToggle:boolean = false;
  trainingToggle:boolean =false;
  message:Message;
  sendMSGCick(){
    this.message = new Message;
    this.messageToggle = true;
  }
  sendMSG(){
    this.message.receiver = this.player.userId;
    this.message.sender = this.user.id;
    this.message.type="2";
    this.commonService.sendMessage(this.message).subscribe(data=>{
      this.messageToggle = false;
    })
  }
  training:Event = new Event;
  addTrainingClick(){
    this.training = new Event;
    this.training.type="2";
    this.training.participant="#"+this.player.id+"#";
    this.trainingToggle=true;
  }
  addTraining(){
    this.commonService.addAEvent(this.training).subscribe(data=>{
      this.trainingToggle = false;
    });
  }
  getUserPlayer(){
    this.commonService.getUserByPlayerId(this.playerId).subscribe(user=>this.user_player=user);
  }
  checkScore(elementId:any){
    document.getElementById("update_"+elementId).style.display="none";
    this.scoreEditting = false;
    document.getElementById("score_edtting_"+elementId).style.display="none";
    document.getElementById("score_not_edtting_"+elementId).style.display="";
    document.getElementById("score_"+elementId).style.display="";
  }
  
  updateScore(eventId:any,score:any){
    if(!isNaN(score)){
      if(score>=0&&score<=200){
        this.commonService.updateScore(this.player.id,eventId,score).subscribe(data=>{
          this.getEventList();
          this.getPlayer();
          this.cancelUpdating(eventId);
        });
      }else{
        alert("The score is invalid, please input correct number.");
      }
    }else{
      alert("The score is invalid, please input correct number.");
    }
  }
  submitNewUserInfo(){
    this.player.firstName = this.user.firstName;
    this.player.lastName = this.user.lastName;
    this.player.gender = this.user.gender;
    this.commonService.updatePlayer(this.player).subscribe(user=>{
      this.editProfile=false;
      this.getPlayer();
    });
  }
  updateScoreClick(eventId:any,score:any){
    document.getElementById("update_"+eventId).focus;
    document.getElementById("update_"+eventId).innerText=score;
    document.getElementById("score_"+eventId).style.display="none";
    document.getElementById("update_"+eventId).style.display="";
    document.getElementById("score_edtting_"+eventId).style.display="";
    document.getElementById("score_not_edtting_"+eventId).style.display="none";
  }

  cancelUpdating(elementId:any){
    document.getElementById("update_"+elementId).style.display="none";
    this.scoreEditting = false;
    document.getElementById("score_edtting_"+elementId).style.display="none";
    document.getElementById("score_not_edtting_"+elementId).style.display="";
    document.getElementById("score_"+elementId).style.display="";
    this.getEventList();
  }
  scoreEditting:boolean = false;
  sort:string = "";
  order:string = "";
  user_player:User = new User;
  genderCommon : Common[] = new Array();
  ngOnInit() {
    this.messageToggle = false;
    this.trainingToggle = false;
    this.editProfile = false;
    this.sort="name";
    this.order="-1";
    this.commonService.getCommon("g",-1).subscribe(gender=>this.genderCommon=gender);
    this.commonService.getUser().subscribe(user=>{
      this.user=user;
      if(user.type=="0"){
        this.playerId = this.route.snapshot.paramMap.get('id');
      }else{
        this.playerId = user.player_id;
      }
      this.getPlayer();
      this.getUserPlayer();
      this.getEventList();
     
    });  
  }
}
