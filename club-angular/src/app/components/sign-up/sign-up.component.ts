import { Component, OnInit } from '@angular/core';
import { CommonService} from '../../services/common/common.service';
import { User } from '../../entities/user';
import { Router } from '@angular/router';
import { MessageService } from '../../services/message/message.service';
import { Club } from 'src/app/entities/club'
import { Team } from 'src/app/entities/team'
import { HttpClient, HttpEvent, HttpEventType, HttpRequest, HttpParams, HttpHeaders} from '@angular/common/http'
import { Observable } from 'rxjs';
import { splitAtColon } from '@angular/compiler/src/util';
import { FormatWidth, formatDate } from '@angular/common';
import { map } from 'rxjs/operators';
import { Common } from 'src/app/entities/common';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/x-www-form-urlencoded' })
};

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})

export class SignUpComponent implements OnInit {
  constructor(private commonService : CommonService, 
    private router:Router,
    private http: HttpClient,
    private messageService:MessageService,
    ) { 
    }

    selectedFile : File;
    fileName = "";
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
          this.user.profile = type;
          reader.readAsDataURL(this.selectedFile);
          reader.onload = () => {
            this.fileName = this.selectedFile.name;
          };
        }else{
          alert("Please upload correct image file.");
          this.selectedFile = null;
          return;
        }
      }
    }
   
    onUpload(name:string){
      if(this.selectedFile==null){
        this.user.profile="user.png";
        return;
      }
      const fd = new FormData;
      const getDotIndex = this.selectedFile.name.lastIndexOf(".");
      const type = this.selectedFile.name.substr(getDotIndex+1);
      if(type=="jpg"||type=="png"||type=="jpeg"){
        fd.append('file',this.selectedFile, name);
        this.http.post('/api/signup/upload',fd).subscribe(data=>{console.log(data)});
      }else{
        return;
      }
    }
  user : User = new User;
  clubs : Club[];
  teams : Team[];

  public signUp(){
    if(!this.repeated){
      this.commonService.singnUp(this.user).subscribe(user => {
        this.onUpload(user.profile);
      }
      );
      this.router.navigate(['/success']);
    }
  }
  public updateTeamList(){
    this.commonService.getTeamsForPublic(this.user.player_clubId).subscribe(teams => {this.teams = teams});
    
  }
  public checkEmail(){
    this.http.get("/api/signup/checkOnly?email="+this.user.email).subscribe(data=>{this.repeated = data['repeated']});
  }
  genderCommon:Common[];
  getCommon(type:string){
    this.commonService.getCommon(type,-1).subscribe(common=>this.genderCommon=common);
  }
  repeated : boolean;
  ngOnInit() {
    this.repeated = false;
    this.commonService.setTitle("Sign Up");
    this.user.type="0";
    this.commonService.getClubsForPublic().subscribe(clubs => this.clubs = clubs);
    this.getCommon("g");//gender
  }
}
