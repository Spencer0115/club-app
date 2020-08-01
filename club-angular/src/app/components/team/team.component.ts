import { Component, OnInit } from '@angular/core';
import { Team } from 'src/app/entities/team';
import { Event } from 'src/app/entities/event';
import { CommonService } from 'src/app/services/common/common.service';

import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import { Location } from '@angular/common';
import { Player } from 'src/app/entities/player';
import { UseExistingWebDriver } from 'protractor/built/driverProviders';
import { User } from 'src/app/entities/user';
import { Message } from 'src/app/entities/message';
@Component({
  selector: 'app-team',
  templateUrl: './team.component.html',
  styleUrls: ['./team.component.css']
})
export class TeamComponent implements OnInit {

  constructor(
    private _location: Location,
    private route: ActivatedRoute,
    private router:Router,
    private commonService:CommonService) { }

  teamId:any= "";
  team:Team = new Team;
  user : User = new User;
  playerList:Player[] = new Array();
  eventList:Event[] = new Array();
  picSrc = "assets/upload-dir/";

  getTeamInfoById(){
    if(this.teamId==null) return;
    this.commonService.getTeamInfoById(this.teamId).subscribe(team=>this.team=team);
  }
  getEventListByTeamId(){
    if(this.teamId==null) return;
    this.commonService.getEventListByTeamId(this.teamId).subscribe(eventList=>this.eventList=eventList);
  }
  getPlayerListByTeamId(){
    if(this.teamId==null) return;
    this.commonService.getPlayerListByTeamId(this.teamId).subscribe(playerList=>this.playerList=playerList);
  }

  backClicked() {
    this._location.back();
  }
  messageToggle:boolean = false;
  trainingToggle:boolean =false;
  message:Message = new Message;
  sendMSGCick(){
    this.message = new Message;
    this.messageToggle = true;
  }
  sendMSG(){
    this.message.receiver = this.team.id;
    this.message.type="1";
    this.commonService.sendMessage(this.message).subscribe(data=>{
      this.messageToggle = false;
    })
  }
  ngOnInit() {
    this.teamId = this.route.snapshot.paramMap.get('id');
    this.commonService.getUser().subscribe(user=>{
      this.user=user;
      if(user.type=="0"){
      }else{
        this.teamId = user.player_teamId;
      }
      this.getTeamInfoById();
      this.getPlayerListByTeamId();
      this.getEventListByTeamId();
    });
  }

}
