import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {User} from "../../core/domain/user";
import {MatTableDataSource} from '@angular/material';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {

  constructor(private router: Router) {
  }

  userDataSource: MatTableDataSource<User>;
  columns = [
    {
      key: 'id',
      header: 'Id',
      cell: row => row.id
    },
    {
      key: 'login',
      header: 'Login',
      cell: row => row.login
    },
    {
      key: 'password',
      header: 'Password',
      cell: row => row.password
    },
    {
      key: 'enabled',
      header: 'Enabled',
      cell: row => row.enabled
    }
  ];
  displayColumns = [...this.columns.reduce(
    (array: string[], column: any) => {
      array.push(column['key']);
      return array;
    }, [])];

  ngOnInit() {
    const usr1: User = new User;

    usr1.id = 1;
    usr1.enabled = true;
    usr1.login = "login";
    usr1.password = "psswd";

    const usr2: User = new User;

    usr1.id = 2;
    usr1.enabled = true;
    usr1.login = "login1";
    usr1.password = "psswd1";

    this.userDataSource.data = [
      usr1,
      usr2
    ];
  }

}
