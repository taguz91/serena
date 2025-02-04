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
      path: '/register',
      name: 'register',
      component: () => import('../views/RegisterView.vue')
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
          path: '/profile',
          name: 'profile',
          component: () => import('../views/app/ProfileView.vue')
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
        },
        {
          path: 'register/copy/:id',
          name: 'classroom-register-copy',
          component: () => import('../views/app/RegisterCopyView.vue')
        },

        {
          path: 'history/:id',
          name: 'history-classroom-registers',
          component: () => import('../views/app/RegisterHistoryView.vue')
        }
      ]
    },
    {
      path: '/public',
      name: 'public',
      children: [
        {
          path: 'check/:id',
          name: 'public-register-check',
          component: () => import('../views/public/CheckView.vue')
        },
        {
          path: 'register/:id',
          name: 'public-register-inscription',
          component: () => import('../views/public/RegisterView.vue')
        },
        {
          path: 'register/:id/:idStudent',
          name: 'public-register-inscription-photo',
          component: () => import('../views/public/RegisterView.vue')
        }
      ]
    },
    {
      path: '/admin',
      name: 'admin',
      beforeEnter: (to, from, next) => {
        const user = JSON.parse(localStorage.getItem('user') || '{}')
        if (user.isAdmin) {
          next()
        } else {
          next({ name: user ? 'app-home' : 'home' })
        }
      },
      children: [
        {
          path: '',
          name: 'admin-home',
          component: () => import('../views/admin/DashboardView.vue')
        },

        // management

        {
          path: 'academic-periods',
          name: 'academic-periods',
          component: () => import('../views/admin/academic-period/ListView.vue')
        },
        {
          path: 'careers',
          name: 'carrera',
          component: () => import('../views/admin/careers/ListView.vue')
        },
        {
          path: 'teachers',
          name: 'teachers',
          component: () => import('../views/admin/teacher/ListView.vue')
        },
        {
          path: 'subjects',
          name: 'subjects',
          component: () => import('../views/admin/subject/ListView.vue')
        },
        {
          path: 'classrooms',
          name: 'classrooms',
          component: () => import('../views/admin/classrooms/ListView.vue')
        },
        {
          path: 'methodologies',
          name: 'methodologies',
          component: () => import('../views/admin/methodology/ListView.vue')
        },

        // information
        {
          path: 'students',
          name: 'students',
          component: () => import('../views/admin/students/ListView.vue')
        },
        {
          path: 'registers',
          name: 'registers',
          component: () => import('../views/admin/registers/ListView.vue')
        },

        // sync

        {
          path: 'sync/students',
          name: 'sync-students',
          component: () => import('../views/admin/sync/StudentView.vue')
        },
        {
          path: 'sync/enroll',
          name: 'sync-enroll',
          component: () => import('../views/admin/sync/EnrollView.vue')
        }
      ]
    }
  ]
})

export default router
