import { Injectable } from '@angular/core';
import { Menu } from '../../entities/menu';
import { Team } from '../../entities/team';
import { Observable, of } from 'rxjs';
import { MessageService } from '../message/message.service';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Club } from 'src/app/entities/club'
import { User } from 'src/app/entities/user';
import { Event } from 'src/app/entities/event';
import { Title } from '@angular/platform-browser';
import { Router, ActivatedRoute, Params } from '@angular/router';
import { catchError, retry } from 'rxjs/operators'
import { Player } from 'src/app/entities/player';
import { Common } from 'src/app/entities/common';
import { Message } from 'src/app/entities/message';


const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json;charset=UTF-8' })
};

@Injectable({
  providedIn: 'root'
})

export class CommonService {

  constructor(
    private titleService : Title,
    private http: HttpClient,
    private messageService: MessageService) { }
    private router : Router;
    private signupUrl = 'api/signup'; 
    private baseUrl: string = 'api/users/';
    private uploadUrl: string = 'api/upload/';
    private publicUrl: string = 'api/public/';
    /**
     * login
     * @param loginPayload 
     */
    login(loginPayload:object) {
      const headers = {
        'Authorization': 'Basic '+btoa("client:crmSuperSecret"),
        'Content-type': 'application/x-www-form-urlencoded',
      }
      return this.http.post('api/oauth/token', loginPayload, {headers});
    }

    refreshToken() {
      const headers = {
        'Authorization': 'Basic '+btoa("client:crmSuperSecret"),
        'Content-type': 'application/x-www-form-urlencoded',
      }
      const body = new HttpParams()
        .set('refresh_token',JSON.parse(window.sessionStorage.getItem('token')).refresh_token)
        .set('grant_type', 'refresh_token')
        .set('client_id','client');
      return this.http.post('api/oauth/token', body, {headers}).subscribe(data => {
        console.log(JSON.stringify(data));
        window.sessionStorage.setItem('token', JSON.stringify(data));
        console.log("token refreshed");
      });
    }
    /**
     * this is a sign up method
     * @param user 
     */
    public singnUp(obj:User): Observable<User>{
      return this.http.post<User>(this.signupUrl, obj, httpOptions)
    }

    public updatePlayer(p:Player):Observable<User>{
      return this.http.post<User>(this.baseUrl + 'updatePlayer?access_token=' + JSON.parse(window.sessionStorage.getItem('token')).access_token,p,httpOptions);
    }
    /**
     * get user information
     * @param user 
     */
    public getUser(): Observable<User>{
      return this.http.post<User>(this.baseUrl + 'getUser?access_token=' + JSON.parse(window.sessionStorage.getItem('token')).access_token,httpOptions);
    }
    public getUserByPlayerId(id:any):Observable<User>{
      return this.http.get<User>(this.baseUrl + 'getUserByPlayerId?id='+id+'&access_token=' + JSON.parse(window.sessionStorage.getItem('token')).access_token,httpOptions);
    }
    /**
     * get menus by login information
     */
    getMenus(): Observable<Menu[]> {
      return this.http.post<Menu[]>(this.baseUrl + 'getMenus?access_token=' + JSON.parse(window.sessionStorage.getItem('token')).access_token,httpOptions);
    }
    /**
     * get club list for public sign up
     */
    getClubsForPublic(): Observable<Club[]> {
      return this.http.get<Club[]>("/api/public/getClubs");
    }
    /**
     * get teams under club for public sign up
     * @param clubId 
     */
    getTeamsForPublic(clubId:number): Observable<Team[]> {
      return this.http.get<Team[]>("api/public/getTeams/"+clubId);
    }

    /**
     * get team infor for current login user
     */
    getTeams(page:number,size:number): Observable<Team[]> {
      return this.http.get<Team[]>(this.baseUrl + 'teams?page='+page+'&size='+size+'&access_token=' + JSON.parse(window.sessionStorage.getItem('token')).access_token,httpOptions);
    }
    getTeamsByName(teamName:string): Observable<Team[]> {
      return this.http.get<Team[]>(this.baseUrl + 'teamsByName'+'?teamName='+teamName+'&access_token=' + JSON.parse(window.sessionStorage.getItem('token')).access_token,httpOptions);
    }
    getTeamInfoById(id):Observable<Team>{
      return this.http.get<Team>(this.baseUrl + 'getTeamById'+'?id='+id+'&access_token=' + JSON.parse(window.sessionStorage.getItem('token')).access_token,httpOptions); 
    }

    /**
     * get events infor 
     */
    getEventById(id:number){
      return this.http.get<Event>(this.baseUrl + 'eventById?id='+id+'&access_token=' + JSON.parse(window.sessionStorage.getItem('token')).access_token,httpOptions);
    }
    getEvents(type:string,order:string,page:number,size:number):Observable<Event[]> {
      return this.http.get<Event[]>(this.baseUrl + 'events?type='+type+'&order='+order+'&page='+page+'&size='+size+'&access_token=' + JSON.parse(window.sessionStorage.getItem('token')).access_token,httpOptions);
    }
    getEventsByName(eventsName:string): Observable<Event[]> {
      return this.http.get<Event[]>(this.baseUrl + 'eventsByName'+'?eventName='+eventsName+'&access_token=' + JSON.parse(window.sessionStorage.getItem('token')).access_token,httpOptions);
    }
    addAEvent(event:Event):Observable<Event[]>{
      return this.http.post<Event[]>(this.baseUrl + 'addEvent'+'?access_token=' + JSON.parse(window.sessionStorage.getItem('token')).access_token, event,httpOptions);
      }
    getEventListByTeamId(teamId:number):Observable<Event[]>{
      return this.http.get<Event[]>(this.baseUrl + 'getEventListByTeamId?teamId='+teamId+'&access_token=' + JSON.parse(window.sessionStorage.getItem('token')).access_token,httpOptions);
    }
    getEventListByPlayerId(playerId:number,sort:string,order:string):Observable<Event[]>{
      return this.http.get<Event[]>(this.baseUrl + 'getEventListByPlayerId?playerId='+playerId+'&access_token=' + JSON.parse(window.sessionStorage.getItem('token')).access_token,httpOptions);
    }
    /**
     * to add a new team
     * @param team 
     */
    addATeam(team:Team):Observable<Team[]>{
      return this.http.post<Team[]>(this.baseUrl + 'addTeam'+'?access_token=' + JSON.parse(window.sessionStorage.getItem('token')).access_token, team,httpOptions);
      }

    deleteTeamById(id:any):Observable<any>{
      return this.http.get(this.baseUrl+'deleteTeam?id='+id+'&access_token=' + JSON.parse(window.sessionStorage.getItem('token')).access_token,httpOptions);
    }
    deleteEventById(id:any):Observable<any>{
      return this.http.get(this.baseUrl+'deleteEvent?id='+id+'&access_token=' + JSON.parse(window.sessionStorage.getItem('token')).access_token,httpOptions);
    }
    setFlag(type:string, id:any):Observable<any>{
      return this.http.get(this.baseUrl+'setFlag?type='+type+'&id='+id+'&access_token=' + JSON.parse(window.sessionStorage.getItem('token')).access_token,httpOptions);
    }
      /**
       * get player info functions
       * @param teamId 
       */
    getPlayerListByTeamId(teamId:number):Observable<Player[]>{
      return this.http.get<Player[]>(this.baseUrl + 'getPlayerListByTeamId?teamId='+teamId+'&access_token=' + JSON.parse(window.sessionStorage.getItem('token')).access_token,httpOptions);
    }
    getPlayerById(id:any):Observable<Player>{
      return this.http.get<Player>(this.baseUrl + 'getPlayerById?id='+id+'&access_token=' + JSON.parse(window.sessionStorage.getItem('token')).access_token,httpOptions);
    }
    getAllPlayers():Observable<Player[]>{
      return this.http.get<Player[]>(this.baseUrl + 'allPlayerByUsername'+'?access_token=' + JSON.parse(window.sessionStorage.getItem('token')).access_token,httpOptions);
    }

    getCommon(type:string,page:number):Observable<Common[]>{
      return this.http.get<Common[]>(this.publicUrl + 'getCommon/'+type+'/'+page,httpOptions);
    }
    deleteCommonById(id:number){
      return this.http.get(this.baseUrl + 'deleteCommon?id='+id+'&access_token=' + JSON.parse(window.sessionStorage.getItem('token')).access_token,httpOptions);
    }


    sendMessage(message:Message):Observable<Message>{
      return this.http.post<Message>(this.baseUrl + 'sendMessage'+'?access_token=' + JSON.parse(window.sessionStorage.getItem('token')).access_token, message,httpOptions);
    }
    getNews(n:number,page:number,jump:number):Observable<Message[]>{
      return this.http.get<Message[]>(this.baseUrl + 'getNews?n='+n+'&page='+page+'&jump='+jump+'&access_token=' + JSON.parse(window.sessionStorage.getItem('token')).access_token,httpOptions);
    }
 
    /**
     * 
     * @param type 
     * @param order 
     * @param page 
     * @param size 
     * @param jump 
     */
    getNewsPagable(type:string,order:string,page:number,size:number,jump:number):Observable<Message[]>{
      return this.http.get<Message[]>(this.baseUrl + 'getNews?type='+type+'&access_token=' + JSON.parse(window.sessionStorage.getItem('token')).access_token,httpOptions);
    }
    /**
     * update Score
     * @param playerId 
     * @param eventId 
     * @param score 
     */
    updateScore(playerId: any, eventId: any, score: any):Observable<any> {
      return this.http.get<any>(this.baseUrl+'updateScore?playerId='+playerId+'&eventId='+eventId+'&score='+score+'&access_token=' + JSON.parse(window.sessionStorage.getItem('token')).access_token,httpOptions);
    }

    getMessageList(userId:number):Observable<Message[]>{
      return this.http.get<Message[]>(this.baseUrl+'getMessages?userId='+userId+'&access_token=' + JSON.parse(window.sessionStorage.getItem('token')).access_token,httpOptions);
    }

    checkCommon(common:Common){
      return this.http.post<any>(this.baseUrl+'checkCommon?access_token=' + JSON.parse(window.sessionStorage.getItem('token')).access_token,common,httpOptions);
    }

    /**
      * show system message
      * @param message 
      */
      public showMessage(message: string) {
      this.messageService.clear();
      this.messageService.add(`Message: ${message}`);
      }

      public addCommon(common:Common):Observable<Common>{
        return this.http.post<Common>(this.baseUrl+'addCommon?access_token=' + JSON.parse(window.sessionStorage.getItem('token')).access_token,common,httpOptions)
      }
    /**
     * set the title for current page
     * @param newTitle 
     */
      public setTitle( newTitle: string) {
        this.titleService.setTitle( newTitle );
      }

      public upLoadFile(fd:FormData) {
        return this.http
          .post("api/signup/upload", fd);
    }

}
