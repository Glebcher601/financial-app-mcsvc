export const USERS_RESOURCE = "/users";
export const SNAPSHOTS_RESOURCE = "/snapshots";


export class UserFormConstants {
  static readonly USER_ID_CTRL: string = "userId";
  static readonly LOGIN_NAME_CTRL: string = "login";
  static readonly PASSWORD_NAME_CTRL: string = "password";
  static readonly IS_ENABLED_CTRL: string = "isEnabled";
  static readonly PERM_GROUP: string = "permissionGroup"
}

export const PERMISSIONS = [
  {
    "id": 0,
    "name": "USER"
  },
  {
    "id": 1,
    "name": "ADMIN"
  },
  {
    "id": 2,
    "name": "SUPERADMIN"
  }
];
