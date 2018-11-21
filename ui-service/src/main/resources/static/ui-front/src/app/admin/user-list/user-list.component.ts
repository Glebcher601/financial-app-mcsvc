import {Component, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {User} from "../../core/domain/user";
import {MatPaginator, MatRow, MatSort, MatTableDataSource} from '@angular/material';
import {UsersService} from "../../core/services/users.service";
import {FilterService} from "../../core/services/filter.service";
import {UserFormComponent} from "../user-form/user-form.component";

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {

  rowSelected: User;
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
  filterValue: string;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(UserFormComponent) userFormComponent: UserFormComponent;

  constructor(private router: Router,
              private activeRoute: ActivatedRoute,
              private usersService: UsersService,
              private filterService: FilterService) {

    this.userDataSource = new MatTableDataSource<User>();
  }

  ngOnInit() {
    this.usersService.getAll().subscribe(data => {
      this.userDataSource.data = data;
    });

    this.filterService.userFilterValue.subscribe(data => this.filterValue = data);
    if (this.filterValue) {
      this.applyFilter(this.filterValue);
    }
  }

  applyFilter(filterValue: string) {
    this.filterService.userFilterSubject.next(filterValue);
    this.userDataSource.filter = filterValue.trim().toLowerCase();
  }

  addUser(): void {
    this.router.navigate(['addPerson'], {relativeTo: this.activeRoute});
  }

  selectRow(row: MatRow) {
    this.userFormComponent.userBehaviourSubject.next(<User> row.valueOf());
  }

}
