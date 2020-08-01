import { Component, OnInit } from '@angular/core';
import { CommonService } from '../../services/common/common.service';
import { Event } from '../../entities/event'
import { Router } from '@angular/router';
import {NgbDateStruct, NgbCalendar,NgbDate} from '@ng-bootstrap/ng-bootstrap';
import { timeInterval } from 'rxjs/operators';
import { Team } from 'src/app/entities/team';
import { Player } from 'src/app/entities/player';
import { Common } from 'src/app/entities/common';
import { User } from 'src/app/entities/user';
import { userInfo } from 'os';

@Component({
  selector: 'app-events',
  templateUrl: './events.component.html',
  styleUrls: ['./events.component.css']
})
export class EventsComponent implements OnInit {
  constructor(private calendar: NgbCalendar,private commonService: CommonService, private router: Router) {}
  model: NgbDateStruct = {year: 2019, month: 11, day:11};
  model2: NgbDateStruct = {year: 2019, month: 11, day:11};

  time = {hour: 13, minute: 30};
  time2 = {hour: 14, minute: 30};
  spinners = true;
  
  calendar1 = false;
  calendar2 = false;

  type="";
  order="startTime";

  toggleCalendar(n:number){
    if(n==1){
      this.calendar1=!this.calendar1;
    }
    if(n==2){
      this.calendar2=!this.calendar2;
    }
  }
  initDateAndTime(){
    let initDate = new Date();
    let year = initDate.getFullYear();
    let month = initDate.getMonth();
    let day = initDate.getDate();
    let hour = initDate.getHours();
    if(hour == 23){
      hour = 0;
      day +=1;
    }else{
      hour +=1;
    }

    this.model.year = year;
    this.model.month = month+1;
    this.model.day = day;
    this.model2.year= year;
    this.model2.month = month+1;
    this.model2.day = day;

    this.time.hour = hour;
    this.time.minute = 0;
    this.time2.hour = hour;
    this.time2.minute = 30;
  }
  hideCalendar(n:number){
    if(n==1){
      this.calendar1=false;
    }
    if(n==2){
      this.calendar2=false;
    }
  }
  toggleSpinners() {
    this.spinners = !this.spinners;
}

  events : Event[] = new Array();

  getEvents(): void {
    this.commonService.getEvents(this.type,this.order,this.page-1,6).subscribe(data =>{
    this.events = data['data']
    this.totalPage = data['totalPage'];
    this.pages = new Array();
    for(var i=1;i<=this.totalPage;i++){
      this.pages.push(i);
    }
    });
  }
  page:number = 0;
  totalPage:number = 0;
  pages:number[] = new Array();
  toPage(n:number){
    this.page+=n;
    this.getEvents();
  }
  jumpPage(n:number){
    this.page=n;
    this.getEvents();
  }

  selectToday() {
      this.model = this.calendar.getToday();
    }
  addEvent:boolean = false; 
  addAEvent(){
    this.initDateAndTime();
    this.addEvent = true;
    this.updateFlag = false;
  }
  cancel(){
    this.updateFlag = false;
    this.addEvent = false;
    this.participantArray = new Array();
    this.participantNameArray = new Array();
    this.event = new Event();
  }

event : Event = new Event;
dataValid = true;
timeValid = true;
submitEvent(){
  this.checkValidation();
  this.event.participant=this.participants;
  if(this.dataValid&&this.timeValid){
  let month = this.model.month<10?"0"+this.model.month:this.model.month;
  let day = this.model.day<10?"0"+this.model.day:this.model.day;
  let month2 = this.model2.month<10?"0"+this.model2.month:this.model2.month;
  let day2 = this.model2.day<10?"0"+this.model2.day:this.model2.day;
  let time = this.time.hour<10?"0"+this.time.hour:this.time.hour;
  let time2 = this.time2.hour<10?"0"+this.time2.hour:this.time2.hour;
  let minutes = this.time.minute<10?"0"+this.time.minute:this.time.minute;
  let minutes2 = this.time2.minute<10?"0"+this.time2.minute:this.time2.minute;
  this.event.startTime = ""+this.model.year+""+month+""+day+" "+time+""+minutes;
  this.event.endTime = ""+this.model2.year+""+month2+""+day2+" "+time2+""+minutes2;
  if(this.event.type=='0'){
    this.event.name = "Game";
  }else if(this.event.type=='1'){
    this.event.name = "Team Training";
  }else if(this.event.type=='2'){
    this.event.name = "Player Training";
  }
  this.event.status = '0';
  for(var i=0;i<this.participantArray.length;i++){
    if(i==0){
      this.event.participantId = "";
      this.event.participantId += "#";
    }
    this.event.participantId += this.participantArray[i];
      this.event.participantId += "#";
  }
  this.commonService.addAEvent(this.event).subscribe(string=>{
    this.getEvents();
    this.event = new Event;
    this.updateFlag = false;
    this.addEvent = false;
  });

}
}
eventSearch = "";
checkValidation(){
  let today = new Date();
  let date: NgbDate = new NgbDate(today.getFullYear(), today.getMonth()+1, today.getDate());
  let date1: NgbDate = new NgbDate(this.model.year, this.model.month, this.model.day);
  let date2: NgbDate = new NgbDate(this.model2.year, this.model2.month, this.model2.day);

  if( (date.before(date1)||date.equals(date1)) && (date1.before(date2)||date1.equals(date2)) ){
    this.dataValid = true;
  }else{
    this.dataValid = false;
  }

}
onEventSearch():void{
  if(this.eventSearch==""){
    this.getEvents();
  }else{
    this.commonService.getEventsByName(this.eventSearch).subscribe(events =>this.events = events);
  }
  
}

teams:Team[] = new Array();
players:Player[] = new Array();
getTeams(){
  this.commonService.getTeams(-1,-1).subscribe(teams=>this.teams=teams);
}
getPlayers(){
  this.commonService.getAllPlayers().subscribe(players=>this.players=players);
}

participantArray = new Array();
participantNameArray = new Array();
participants ="";
selectParticipant(check:boolean,value:number,name:string){
  if(check){
    this.participants ="";
    if(this.event.type=='0'&&this.participantArray.length<2){
      this.participantArray.push(value);
      this.participantNameArray.push(name);
      document.getElementById("check_"+value).setAttribute("checked","true");
    }else if(this.event.type!='0'){
      this.participantArray.push(value);
      this.participantNameArray.push(name);
    }
    for(var i=0;i<this.participantNameArray.length;i++){
      this.participants+=this.participantNameArray[i];
      if(i!=this.participantNameArray.length-1){
        this.participants+=",";
      }
    }
    if(this.event.type=='0'&&this.participantArray.length==2){
     let list = document.getElementsByName("checkTeam");
     for(let i=0;i<list.length;i++){
       if(list[i].getAttribute("checked")!="true"){
        list[i].setAttribute("disabled","disabled");
       }
     }
    }
}else{
  this.participants ="";
  document.getElementById("check_"+value).setAttribute("checked","false");
  if(this.event.type=='0'&&this.participantArray.length==2){
      let list = document.getElementsByName("checkTeam");
      for(let i=0;i<list.length;i++){
        list[i].removeAttribute("disabled");
      }
  }
  for(var i=0;i<this.participantNameArray.length;i++){
    if(this.participantArray[i]==value){
      this.participantArray.splice(i);
      this.participantNameArray.splice(i);
    }
  }
  for(var i=0;i<this.participantNameArray.length;i++){
    this.participants+=this.participantNameArray[i];
    if(i!=this.participantNameArray.length-1){
      this.participants+=",";
    }
  }
}
}
deleteEvent(id:any){
  if(confirm("Are you sure to delete the event?"))
  this.commonService.deleteEventById(id).subscribe(data=>{
    this.getEvents();
  });
}
updateFlag:boolean = false;
updateEvent(id:number){
  this.updateFlag = true;
  this.addEvent = false;
  this.commonService.getEventById(id).subscribe(event=>{
    this.event = event;
    this.participants = event.participant;

    this.model = {year:Number(event.startTime.substring(0,4)),month:Number(event.startTime.substring(4,6)),day:Number(event.startTime.substring(6,8))};
    this.model2 = {year:Number(event.endTime.substring(0,4)),month:Number(event.endTime.substring(4,6)),day:Number(event.endTime.substring(6,8))};
    this.time = {hour:Number(event.startTime.substring(9,11)),minute:Number(event.startTime.substring(11,13))};
    this.time2 = {hour:Number(event.endTime.substring(9,11)),minute:Number(event.endTime.substring(11,13))};

  })
}
flagEvent(id:any){
  this.commonService.setFlag("e",id).subscribe(data=>{
    this.getEvents();
  })
}
clearParticipants(){
  this.participants = "";
  this.participantArray = new Array();
  this.participantNameArray = new Array();
  this.getPlayers();
  this.getTeams();
}

eventCommon:Common[] = new Array();
user : User = new User;
ngOnInit() {
  this.commonService.getUser().subscribe(user=>this.user=user);
  this.page=1;
  this.addEvent=false;
  if(window.sessionStorage.getItem('username') == "" || window.sessionStorage.getItem('username') == null){
    this.router.navigate(['/login']);
  }
  this.getEvents();
  this.commonService.getCommon("e",-1).subscribe(common=>this.eventCommon=common);
  this.initDateAndTime();
}
}
