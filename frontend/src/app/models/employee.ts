import { Time } from "@angular/common";
import { Position } from "./Position";

export interface Employee {
    id: number;
    contract_expire?: Date;
    code?: number;
    status: boolean;
    start_time?: Time;
    end_time?: Time;
    role?: string;
    created_date?: Date;
    modified_date?: Date;
    deleted?: boolean;
    fullname: string;
    birthday?: Date;
    email?: string;
    phoneNumber?: string;
    address?: string;
    notes?: string;
    position?: Position;
    image?: string;
}