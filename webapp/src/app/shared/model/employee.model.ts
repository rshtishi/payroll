// Generated using typescript-generator version 2.0.400 on 2020-08-05 20:08:25.

export interface Employee {
    id?: number;
    firstname?: string;
    lastname?: string;
    address?: string;
    phone?: string;
    departmentId?: number;
}

export interface EmployeeCountChangeModel {
    departmentId?: number;
    action?: string;
    typeName?: string;
}

export interface ErrorDetail {
    title?: string;
    status?: number;
    detail?: string;
    timeStamp?: number;
    developerMessage?: string;
    errors?: { [index: string]: ValidationError[] };
}

export interface ValidationError {
    code?: string;
    message?: string;
}

export const enum EmployeeActionEnum {
    CREATE = 'CREATE',
    UPDATE = 'UPDATE',
    DELETE = 'DELETE',
}
