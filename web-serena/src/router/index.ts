import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/welcome',
      name: 'welcome',
      component: () => import('../views/WelcomeView.vue')
    },
    {
      path: '/app',
      name: 'app',
      children: [
        {
          path: '',
          name: 'app-home',
          component: () => import('../views/app/HomeView.vue')
        },
        {
          path: 'student/:id',
          name: 'student-report',
          component: () => import('../views/app/StudentView.vue')
        },
        {
          path: 'classroom/:id',
          name: 'classroom-report',
          component: () => import('../views/app/ClassroomView.vue')
        },
        {
          path: 'register/:id',
          name: 'classroom-register',
          component: () => import('../views/app/RegisterView.vue')
        }
      ]
    }
  ]
})

export default router
