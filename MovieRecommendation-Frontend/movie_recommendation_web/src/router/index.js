import {createRouter, createWebHistory} from 'vue-router'

const Login = () => import('../views/Login.vue')
const Home = () => import('../views/home/Home')
const Register = () => import('../views/user/Register')
const ForgetPassword = () => import('../views/user/ForgetPassword')

const routes = [
  {
    path: '',
    redirect: '/login'
  },
  {
    path: '/login',
    component: Login,
    meta: {title: '登录'},
  },
  {
    path: '/register',
    component: Register,
    meta: {title: '注册'},
  },
  {
    path: '/forgetPassword',
    component: ForgetPassword,
    meta: {title: '忘记密码'},
  },
  {
    path: '/home',
    component: Home,
    meta: {title: '首页'}
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

// 设置页面标题
router.beforeEach((to, from, next) => {
  document.title = to.matched[0].meta.title
  next()
})

export default router