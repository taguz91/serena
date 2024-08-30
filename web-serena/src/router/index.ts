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
    },
    {
      path: '/admin',
      name: 'admin',
      children: [
        {
          path: '',
          name: 'admin-home',
          component: () => import('../views/admin/DashboardView.vue')
        },

        // academic-periods

        {
          path: 'academic-periods',
          name: 'academic-periods',
          component: () => import('../views/admin/academic-period/ListView.vue')
        },

        // subjects
        {
          path: 'subjects',
          name: 'subjects',
          component: () => import('../views/admin/subject/ListView.vue')
        },

        // classrooms

        {
          path: 'classrooms',
          name: 'classrooms',
          component: () => import('../views/admin/classrooms/ListView.vue')
        },

        // students
        {
          path: 'students',
          name: 'students',
          component: () => import('../views/admin/students/ListView.vue')
        }
      ]
    }
  ]
})

export default router
