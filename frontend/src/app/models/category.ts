import { User } from "./User";

export interface Category {
    id: number;
    name: string;
    notes: string;
    deleted: boolean;
    enabled: boolean;
    deleted_date: Date;
    last_modified: Date;
    created_date: Date;
    images?: string;
    deleted_author?: User;
    created_author_id?: number;
}