import { Component, OnInit } from '@angular/core';
import {User} from '../../entities/user';
import { MessageService } from '../../services/message/message.service';
import {CommonService} from '../../services/common/common.service';
import { Router } from '@angular/router';
import { HttpParams } from '@angular/common/http';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(private messageService: MessageService, private commonService : CommonService, private router : Router) { }
  user : User = new User;
  onsubmit() {
    const body = new HttpParams()
      .set('username', this.user.username)
      .set('password', this.user.password)
      .set('grant_type', 'password')
      .set('client_id','client');
    this.commonService.login(body).subscribe(data => {
      window.sessionStorage.setItem('token', JSON.stringify(data));
      window.sessionStorage.setItem('refreshToken', JSON.stringify(data));
      window.sessionStorage.setItem('username', this.user.username);
      this.router.navigate(['/user']);
    }, error => {
        this.commonService.showMessage("Loggin Failed due to "+error.error.error_description);
    });
  }

  ngOnInit() {
    this.commonService.setTitle("Login");
  }

}