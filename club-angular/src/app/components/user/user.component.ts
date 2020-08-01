import { Component, OnInit , Input} from '@angular/core';
import { CommonService } from 'src/app/services/common/common.service';
import { ActivatedRoute, Params , Router} from '@angular/router';
import { Subscription } from 'rxjs';
import { User } from 'src/app/entities/user';
import {Menu} from '../../entities/menu';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {

  constructor(private router: Router,private commonService: CommonService, private route : ActivatedRoute) { }

  user : User = new User;
  private route$ : Subscription;
  isCollapsed = true;
  clubMenuCollapsed = true;
  
  picSrc = "assets/upload-dir/";
  
  getUser(): void {
    this.commonService.getUser().subscribe(user=>{
      this.user=user;
      if(user==null){
        this.router.navigate(['/login']);
      }
    });
  }

  menus : Menu[];
  getMenus(): void {
    this.commonService.getMenus().subscribe(menus =>this.menus = menus);
  }

  doLogout(){
    window.sessionStorage.clear();
    this.router.navigate(['/login']);
  }



  ngOnInit() {
    //this.commonService.refreshToken();
    if(window.sessionStorage.getItem('username') == "" || window.sessionStorage.getItem('username') == null){
      this.router.navigate(['/login']);
    }
    this.commonService.setTitle("User");
    this.getUser();
    this.getMenus();   
    this.router.navigate(['/user/home']);
  }

}
