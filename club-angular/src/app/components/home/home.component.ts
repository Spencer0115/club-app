import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/entities/user';
import { CommonService } from 'src/app/services/common/common.service';
import { Common } from 'src/app/entities/common';
import { Message } from 'src/app/entities/message';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(private commonService: CommonService) { }
  
  picSrc = "assets/upload-dir/";
  events : Event[] = new Array();
  order="";
  eventType="";
  type="";
  getEvents(): void {
    this.commonService.getEvents(this.eventType,this.order,this.page-1,8).subscribe(data =>{
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

  newsList : Message[] = new Array();
  newsList1 : Message[] = new Array();
  messages:Message[] = new Array();

  sports : Common[]= new Array();
  places : Common[]= new Array();
  evetsTypes : Common[]= new Array();

  getCommon(){
    this.commonService.getCommon("s",this.page_s).subscribe(data=>{
      this.sports = data['data'];
      this.totalPage_s = data['totalPage'];
      this.pages_s = new Array();
      for(var i=1;i<=this.totalPage_s;i++){
        this.pages_s.push(i);
      }  
    });
    this.commonService.getCommon("e",this.page_e).subscribe(data=>{
    this.evetsTypes = data['data'];  
    this.totalPage_e = data['totalPage'];
    this.pages_e = new Array();
    for(var i=1;i<=this.totalPage_e;i++){
      this.pages_e.push(i);
    }  
  });
  }
page_s:number = 0;
page_e:number = 0;
page_n:number = 0;
totalPage_s:number = 0;
totalPage_e:number = 0;
totalPage_n:number = 0;
pages_e:number[] = new Array();
pages_n:number[] = new Array();
pages_s:number[] = new Array();

toPage_common(type:string,n:number){
  if(type=='s'){
    this.page_s +=n;
    this.getCommon();
  }else if(type == 'e'){
    this.page_e +=n;
    this.getCommon();
  }else if(type == 'n'){
    this.page_n +=n;
    this.getMoreNews();
  }
}
jumpPage_common(type:string,n:number){
  if(type=='s'){
    this.page_s = n-1;
    this.getCommon();
  }else if(type == 'e'){
    this.page_e = n-1;
    this.getCommon();
  }else if(type == 'n'){
    this.page_n = n-1;
    this.getMoreNews();
  }
}
  publishNews:boolean;
  publishNewsClick(){
    this.publishNews=true;
    this.addCommon = false;
    this.news = new Message;
  }
  news:Message = new Message;
  publichNews(){
    this.news.type="3";//news 
    this.news.sender = this.user.id;
    this.commonService.sendMessage(this.news).subscribe(news=>{
      this.news = news;
      this.onUpload();
      this.commonService.sendMessage(this.news).subscribe(news=>{
        this.getNews();
        this.getMoreNews();
        this.publishNews=false;
      });
    });

  }
  getNews(){
    this.commonService.getNews(5,-1,-1).subscribe(news=>{
      this.newsList=news;
    });
  }
  getMoreNews(){
    this.commonService.getNews(0,this.page_n,5).subscribe(data=>{
      this.newsList1 = data['data'];
      this.totalPage_n = data['totalPage'];
      this.pages_n = new Array();
      for(var i=1;i<=this.totalPage_n;i++){
        this.pages_n.push(i);
      } 
    });
  }
  selectedFile : File;
  onFileChange(event) {
    let reader = new FileReader();
    if(event.target.files && event.target.files.length > 0) {
      this.selectedFile = event.target.files[0];
      const getDotIndex = this.selectedFile.name.lastIndexOf(".");
      const type = this.selectedFile.name.substr(getDotIndex+1);
      if(type=="jpg"||type=="png"||type=="jpeg"){
        if(this.selectedFile.size/1000>200){
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
      let  newName= this.news.title.replace(' ','')
      newName = "news-"+newName+"_"+this.news.id+"."+type;
      this.news.src = newName;
      fd.append('file',this.selectedFile, newName);
      this.commonService.upLoadFile(fd).subscribe(data=>{console.log(data)});
    }else{
      return;
    }
  }
  common : Common = new Common;
  addCommon : boolean = false;
  commonRepeat : boolean = false;
  addCommonClick(type:any){
    this.common = new Common;
    this.common.type = type;
    this.addCommon = true;
    this.publishNews=false;
    this.commonRepeat = false;
  }
  addCommonSubmit(){
    this.commonService.checkCommon(this.common).subscribe(data=>{
      if(data['repeated'] == true){
        this.commonRepeat = true;
        return;
      }else{
        if(this.common.name!=null&&this.common.name.trim()!=""){
          this.commonService.addCommon(this.common).subscribe(data=>{
            this.addCommon = false;
            this.getCommon();
          });
        }else{
          alert("Please input type name.");
        }
      }
    });
  }
  deleteCommon(id:number){
    if(confirm("Are you sure to delete this record?")){
      this.commonService.deleteCommonById(id).subscribe(data=>{
        this.getCommon();
      });
    }
  }
  user:User = new User;
  ngOnInit() {
    this.publishNews = false;
    this.page=1;
    this.addCommon = false;
    this.commonService.getUser().subscribe(user=>{
      this.user = user;
      this.type = user.type;
      this.commonService.getMessageList(this.user.id).subscribe(messages=>this.messages = messages);
      this.getEvents();
      this.getCommon();
      this.getNews();
      this.getMoreNews();
    });
  }


}
