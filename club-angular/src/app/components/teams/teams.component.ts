import { Component, OnInit } from '@angular/core';
import { CommonService } from '../../services/common/common.service';
import { Team } from '../../entities/team'
import { Router } from '@angular/router';
import { User } from 'src/app/entities/user';
import { HttpClient } from '@angular/common/http';
import { Common } from 'src/app/entities/common';
@Component({
  selector: 'app-teams',
  templateUrl: './teams.component.html',
  styleUrls: ['./teams.component.css']
})
export class TeamsComponent implements OnInit {

  constructor(private http: HttpClient,private commonService: CommonService, private router: Router) {}
  picSrc = "assets/upload-dir/";
  selectedFile : File;
  onFileChange(event) {
    let reader = new FileReader();
    if(event.target.files && event.target.files.length > 0) {
      this.selectedFile = event.target.files[0];
      const getDotIndex = this.selectedFile.name.lastIndexOf(".");
      const type = this.selectedFile.name.substr(getDotIndex+1);
      if(type=="jpg"||type=="png"||type=="jpeg"){
        if(this.selectedFile.size/1000>500){
          alert("The image is too big.");
          this.selectedFile = null;
          return;
        }
        reader.readAsDataURL(this.selectedFile);
        reader.onload = () => {
          this.selectedFile.name;
        };
      }else{
        alert("Please upload correct image file.");
        this.selectedFile = null;
        return;
      }
    }
  }
 
  onUpload(){
    if(this.selectedFile==null){
      return;
    }
    const fd = new FormData;
    const getDotIndex = this.selectedFile.name.lastIndexOf(".");
    const type = this.selectedFile.name.substr(getDotIndex+1);
    if(type=="jpg"||type=="png"||type=="jpeg"){
      let  newName= this.team.name.replace(' ','')
      newName = "team-profile-"+newName+"."+type;
      this.team.profile = newName;
      fd.append('file',this.selectedFile, newName);
      this.commonService.upLoadFile(fd).subscribe(data=>{console.log(data)});
    }else{
      return;
    }
  }

  teams : Team[];
  user : User = new User;

  getTeams(): void {
    this.commonService.getTeams(this.page-1,9).subscribe(data =>{
    this.teams = data['data']
    this.totalPage = data['totalPage'];
    this.pages = new Array();
    for(var i=1;i<=this.totalPage;i++){
      this.pages.push(i);
    }
    });
  }
  page:number;
  totalPage:number;
  pages:number[];
  toPage(n:number){
    this.page+=n;
    this.getTeams();
  }
  jumpPage(n:number){
    this.page=n;
    this.getTeams();
  }
  getTeamByName():void{
    this.commonService.getTeamsByName(this.teamSearch).subscribe(teams =>{
      this.teams = teams;
      this.totalPage = 1;
    });
  }
  fileApi = "http://localhost:8080/users/getPics/";
  addTeam:boolean;

  addATeam(){
    this.addTeam = true;
    this.team = new Team;
  }
  cancleAddTeam(){
    this.addTeam = false;
    this.updateTeam = false;
    this.team = new Team;
  }
team : Team = new Team;
submitTeam(){
  if(!this.repeated){
  this.addTeam=false;
  this.updateTeam = false;
  if(window.sessionStorage.getItem('username') == "" || window.sessionStorage.getItem('username') == null){
    this.router.navigate(['/login']);
  }
  this.onUpload();
  if(this.user.profile==null||this.user.profile==""){
    this.user.profile="user.png";
  }
  this.commonService.addATeam(this.team).subscribe(teams =>{
    this.getTeams();
  });
  
}
}
repeated:boolean;
public checkTeam(){
  if(this.team.name!=""){
    this.http.get("/api/users/checkTeam?teamName="+this.team.name+'&access_token=' + JSON.parse(window.sessionStorage.getItem('token')).access_token).subscribe(data=>{this.repeated = data['repeated']});
  }else{
    this.repeated=false;
  }
}

teamSearch= "";
public onTeamSearch(){
  this.repeated = false;
  if(this.teamSearch!=""){
    this.getTeamByName();
  }else{
    this.getTeams();
  }
}
deleteTeamById(id:any){
 if(confirm("Are you sure to delete this team?")){
   this.commonService.deleteTeamById(id).subscribe(data=>{
    this.getTeams();
   })
 }
}
updateTeam : boolean;
updateTeamById(id:any){
  this.commonService.getTeamInfoById(id).subscribe(team=>this.team=team);
  this.updateTeam = true;
 }

sportCommon:Common[];
  ngOnInit() {
    this.page=1;
    this.repeated=false;
    this.addTeam=false;
    this.updateTeam=false;
    if(window.sessionStorage.getItem('username') == "" || window.sessionStorage.getItem('username') == null){
      this.router.navigate(['/login']);
    }
    this.getTeams();
    this.commonService.getCommon("s",-1).subscribe(common=>this.sportCommon=common);

  }
}
