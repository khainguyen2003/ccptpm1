import { Department } from "src/app/models/Department";
import { Jobtitle } from "src/app/models/Jobtitle";

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

export const FAKE_JOBTITLE: Jobtitle[] = [
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