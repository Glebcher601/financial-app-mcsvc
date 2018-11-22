import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {User} from "../../core/domain/user";
import {MatDialog, MatPaginator, MatRow, MatSort, MatTableDataSource} from '@angular/material';
import {UsersMockTestService} from "../../core/services/users.service";
import {FilterService} from "../../core/services/filter.service";
import {UserFormComponent} from "../user-form/user-form.component";
import {filter, first} from "rxjs/operators";
import {DeleteDialogComponent} from "../../shared/delete-dialog/delete-dialog.component";

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit, AfterViewInit {
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
      }, []), "deleteButton"];
  filterValue: string;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(UserFormComponent) userFormComponent: UserFormComponent;

  constructor(private dialog: MatDialog,
              private router: Router,
              private activeRoute: ActivatedRoute,
              private usersService: UsersMockTestService,
              private filterService: FilterService) {

    this.userDataSource = new MatTableDataSource<User>();
  }

  ngOnInit() {
    this.userFormComponent.showFormBehaviorSubject
        .pipe(filter<boolean>(value => !value))
        .subscribe(value => this.populateDataTable());

    this.filterService.userFilterValue.subscribe(data => this.filterValue = data);
    if (this.filterValue) {
      this.applyFilter(this.filterValue);
    }
  }

  ngAfterViewInit(): void {
    this.userDataSource.paginator = this.paginator;
    this.userDataSource.sort = this.sort;
  }

  private populateDataTable(): void {
    this.usersService.getAll().pipe(first()).subscribe(data => {
      this.userDataSource.data = data;
    });
  }

  applyFilter(filterValue: string) {
    this.filterService.userFilterSubject.next(filterValue);
    this.userDataSource.filter = filterValue.trim().toLowerCase();
  }

  addUser(): void {
    this.showUserFormComponent(new User);
  }

  selectRow(row: MatRow) {
    this.showUserFormComponent(<User> row.valueOf());
  }

  private showUserFormComponent(user: User): void {
    this.userFormComponent.userBehaviourSubject.next(user);
  }

  openDeleteDialog(user: User) {
    const dialogRef = this.dialog.open(DeleteDialogComponent, {data: user});

    dialogRef.afterClosed()
        .pipe(filter(data => data))
        .subscribe(data =>
            this.usersService.delete(user.id)
                .subscribe(deleted => this.populateDataTable()));
  }

}
