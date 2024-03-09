export interface category {
    id: number;
    name: string;
    notes: string;
    deleted: boolean;
    deleted_date: Date;
    deleted_author: number;
    modified_date: Date;
    created_date: Date;
    image: string;
    created_author_id: number;
}