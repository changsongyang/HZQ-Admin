import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

import Layout from '../views/manager/layout/Layout'

export const constantRouterMap = [
  {
    path: '/login',
    component: () => import('@/views/manager/login/index'),
    hidden: true
  },
  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },
  {
    path: '',
    component: Layout,
    name: 'index',
    children: [{
      path: 'index',
      meta: { title: 'Welcome', icon: 'welcome' },
      component: () => import('@/views/manager/dashboard/index')
    }]
  }

]

export default new Router({
  mode: 'history', // 后端支持可开
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap
})

export const asyncRouterMap = [
  {
    path: '',
    component: Layout,
    children: [
      {
        path: 'icon',
        name: 'Icon',
        component: () => import('@/views/manager/svg-icons/index'),
        meta: { title: '图标管理', icon: 'example' }
      }
    ]
  },
  {
    path: '',
    component: Layout,
    name: 'System',
    meta: { title: '系统管理', icon: 'system' },
    children: [
      {
        path: 'sysUser',
        name: 'User',
        component: () => import('@/views/manager/system/user/index'),
        meta: { title: '用户管理', icon: 'user' },
        code: 'MENU_SYSTEM_USER'
      },
      {
        path: 'sysRole',
        name: 'Role',
        component: () => import('@/views/manager/system/role/index'),
        meta: { title: '角色管理', icon: 'role' },
        code: 'MENU_SYSTEM_ROLE'
      },
      {
        path: 'sysMenu',
        name: 'Menu',
        component: () => import('@/views/manager/system/menu/index'),
        meta: { title: '资源管理', icon: 'class' },
        code: 'MENU_SYSTEM_MENU'
      },
      {
        path: 'sysDict',
        name: 'Dict',
        component: () => import('@/views/manager/system/dict/index'),
        meta: { title: '数据字典管理', icon: 'dict' },
        code: 'MENU_SYSTEM_DICT'
      }
    ]
  },
  { path: '*', redirect: '/index', hidden: true }
]
