import { Employee } from './../../../models/employee';
import { Department } from "src/app/models/Department";
import { Position } from "src/app/models/Position";

export const FAKE_DEPARTMENT: Department[] = [
    {
      id: 1,
      name: 'Kế toán',
      active: true
    }, {
      id: 2,
      name: 'Kiểm toán',
      active: true
    },
    {
      id: 3,
      name: 'Phân phối',
      active: true
    },
    {
      id: 4,
      name: 'Tiếp thị',
      active: true
    }
];

export const FAKE_POSITION: Position[] = [
    {
      id: 1,
      name: 'Kế toán',
      active: true
    }, {
      id: 2,
      name: 'Kiểm toán',
      active: true
    },
    {
      id: 3,
      name: 'Nhân viên',
      active: true
    },
    {
      id: 4,
      name: 'Trưởng phòng',
      active: true
    }
];

export const FAKE_EMPLOYEE: Employee[] = [
  {
    id: 1,
    fullname: 'Nguyễn Quang Huy',
    email: 'nguyenquanghuylt2002@gmail.com',
    status: true,
    image: 'https://i.pinimg.com/564x/b4/63/37/b463371b708ddd5ed4ed8945840cb0ab.jpg',
  },
  {
    id: 2,
    fullname: 'Khổng Thị Hoài Thanh',
    email: 'khongthihoaithanhlt2003@gmail.com',
    status: true,
    image: 'https://i.pinimg.com/736x/f9/a7/3c/f9a73ca8d86883de90499a06a64cdae8.jpg',
  },
  {
    id: 3,
    fullname: 'Triệu Thị Khánh Linh',
    email: 'linhnhi2003@gmail.com',
    status: true,
    image: 'https://i.pinimg.com/564x/81/aa/e4/81aae4c00d5e30f0210d855e05be11ac.jpg',
  },
];