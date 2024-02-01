import { uniqueId } from 'lodash';
import FormatListBulletedIcon from '@mui/icons-material/FormatListBulleted';
import SettingsIcon from '@mui/icons-material/Settings';
import PersonIcon from '@mui/icons-material/Person';
import AddTaskIcon from '@mui/icons-material/AddTask';
import DomainVerificationIcon from '@mui/icons-material/DomainVerification';

const Menuitems = [
  {
    navlabel: true,
    subheader: 'Home',
  },
  {//다 필수값입니다.
    id: uniqueId(),
    title: 'Board',
    icon: FormatListBulletedIcon,
    href: '/board',
  },
  {
    id: uniqueId(),
    title: 'Work',
    icon: DomainVerificationIcon,
    href: '/work',
  },
  {
    navlabel: true,
    subheader: 'Setting',
  },
  {
    id: uniqueId(),
    title: 'Account',
    icon: SettingsIcon,
    href: '/account',
  },
  {
    id: uniqueId(),
    title: 'Auth',
    icon: PersonIcon,
    href: '/auth',
  },
  {
    navlabel: true,
    subheader: 'Sample',
  },
  {
    id: uniqueId(),
    title: 'page1',
    icon: AddTaskIcon,
    href: '/sample',
  },
];

export default Menuitems;
