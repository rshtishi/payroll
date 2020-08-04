export class AppSettings {

    public static APP_NAME = 'payroll';

    public static SERVER_URL = 'https://localhost:5555';

    public static API_ENDPOINT = '/payroll';

    public static LOGIN_ENDPOINT = AppSettings.SERVER_URL + AppSettings.API_ENDPOINT + '/oauth2/oauth/token';

    public static USER_ENDPOINT = AppSettings.SERVER_URL + AppSettings.API_ENDPOINT + '/oauth2/user';

    public static ACCESS_TOKEN = 'access_token';

    public static CURRENT_USER = 'current_user';

    public static DEPARTMENT_ENDPOINT = AppSettings.SERVER_URL + AppSettings.API_ENDPOINT + "/department";

    public static EMPLOYEE_ENDPOINT = AppSettings.SERVER_URL + AppSettings.API_ENDPOINT + "/employee";

    public static AUTHORIZATION_HEADER_NAME = 'Authorization';
}