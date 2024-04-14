import { Time } from "@angular/common";
import { Position } from "./Position";

export interface Employee {
    id: number;
    contract_expire?: Date;
    status?: boolean;
    start_date?: Date;
    role?: number;
    created_date?: Date;
    modified_date?: Date;
    deleted?: boolean;
    fullname: string;
    birthday?: Date;
    email?: string;
    phoneNumber?: string;
    address?: string;
    notes?: string;
    image: string;
    name?: string;
    pass?: string;
}

export interface EmployeeResponse {
    employees: Employee[];
    curPage: number;
    totalPage: number;
    totalElements: number;
    pageSize: number;
    numberOfElements: number;
}