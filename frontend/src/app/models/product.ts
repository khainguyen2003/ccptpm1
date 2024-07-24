import { Category } from './Category';
import { User } from './User';
export interface Product {
    id?: string;
    name?: string;
    code?: string;
    price?: number;
    quantity?: number;
    category?: Category;
    description?: string;
    thumb?: string;
    images?: string[];
    createdDate?: Date;
    creator?: User;
    deleted?: boolean;
    isStopCell?: boolean;
    isDirectCell?: boolean;
    weight?: string;
    rate?: number;
    attr?: any[];
    isPulished?: boolean;
    isDraft?: boolean;
    slug?: string;
    import_price?: number;
    sell_price?: number;
}