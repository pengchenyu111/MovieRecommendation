import Vue from 'vue'
import Router from 'vue-router'

const Login = () => import('../views/Login.vue')
const Home = () => import('../views/home/Home')
const Register = () => import('../views/user/Register')
const ForgetPassword = () => import('../views/user/ForgetPassword')
const Rank = () => import('../views/rank/Rank')
const HistoryTop = () => import('../views/rank/HistoryTop')
const RecentTop = () => import('../views/rank/RecentTop')
const AlsUserTop = () => import('../views/rank/AlsUserTop')
const PerTagTop = () => import('../views/rank/PerTagTop')
const StreamRatingTop = () => import('../views/rank/StreamRatingTop')
const MovieHome = () => import('../views/movie/MovieHome')

Vue.use(Router)

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
  },
  {
    path: '/rank',
    component: Rank,
    meta: {title: '全部榜单'},
    children: [
      {
        path: '',
        redirect: 'history_top'
      },
      {
        path: 'history_top',
        component: HistoryTop
      },
      {
        path: 'recent_top',
        component: RecentTop
      },
      {
        path: 'per_tag_top',
        component: PerTagTop
      },
      {
        path: 'als_user_top',
        component: AlsUserTop
      },
      {
        path: 'stream_rating_top',
        component: StreamRatingTop
      }
    ]
  },
  {
    path: '/movie/:doubanId',
    component: MovieHome,
    meta: {title: '电影详情'}
  }
]

const router = new Router({
  routes,
  mode: 'history'
})

// 设置页面标题
router.beforeEach((to, from, next) => {
  document.title = to.matched[0].meta.title
  next()
})

export default router
