<template>
  <div class="h-screen w-screen flex">
    <aside class="w-[280px]">
      <div class="flex justify-center my-6">
        <AppIcon />
      </div>

      <NMenu mode="vertical" :options="menuOptions" />
    </aside>

    <main class="w-full h-full bg-">
      <TopNav title="Dashboard" />

      <NModalProvider>
        <div class="py-10 px-14 bg-slate-50 min-h-full">
          <RouterView />
        </div>

        <FooterSimple />
      </NModalProvider>
    </main>
  </div>
</template>

<script setup lang="ts">
import { h, type Component } from 'vue'
import { RouterLink, RouterView, useRouter } from 'vue-router'

import {
  Calendar,
  Certificate,
  Dashboard,
  Home,
  LayoutBoard,
  LayoutBoardSplit,
  ListCheck,
  School,
  User,
  Users
} from '@vicons/tabler'
import { NIcon, NMenu, NModalProvider, type MenuOption } from 'naive-ui'
import AppIcon from '../shared/AppIcon.vue'
import TopNav from '../shared/TopNav.vue'
import FooterSimple from '../shared/FooterSimple.vue'

const router = useRouter()

const renderIcon = (icon: Component) => {
  return () => h(NIcon, null, { default: () => h(icon) })
}

const renderLink = (to: string, label: string) => {
  return () =>
    h(
      RouterLink,
      {
        to: {
          name: to
        }
      },
      { default: () => label }
    )
}

const renderLinkButton = (to: string, label: string) => {
  return () =>
    h(
      'button',
      {
        class: 'text-left w-full py-2 px-4',
        onClick: () => {
          localStorage.setItem('appMode', 'app')
          router.push({ name: to })
        }
      },
      { default: () => label }
    )
}

const menuOptions: MenuOption[] = [
  {
    key: 'home',
    label: renderLink('admin-home', 'Inicio'),
    icon: renderIcon(Home)
  },
  {
    key: 'spacer-administrar',
    label: 'Administrar',
    disabled: true
  },
  {
    key: 'carrera',
    label: renderLink('carrera', 'Carreras'),
    icon: renderIcon(Certificate)
  },
  {
    key: 'academic-period',
    label: renderLink('academic-periods', 'Periodos Académicos'),
    icon: renderIcon(Calendar)
  },
  {
    key: 'teachers',
    label: renderLink('teachers', 'Docentes'),
    icon: renderIcon(Users)
  },

  {
    key: 'subjects',
    label: renderLink('subjects', 'Materias'),
    icon: renderIcon(LayoutBoard)
  },
  {
    key: 'classrooms',
    label: renderLink('classrooms', 'Clases'),
    icon: renderIcon(School)
  },
  {
    key: 'methodologies',
    label: renderLink('methodologies', 'Metodologías'),
    icon: renderIcon(LayoutBoardSplit)
  },
  {
    key: 'spacer',
    label: 'Consultar',
    disabled: true
  },
  {
    key: 'students',
    label: renderLink('students', 'Estudiantes'),
    icon: renderIcon(User)
  },
  {
    key: 'registers',
    label: renderLink('registers', 'Registros'),
    icon: renderIcon(ListCheck)
  },
  {
    key: 'spacer',
    label: 'App',
    disabled: true
  },
  {
    key: 'go-app',
    label: renderLinkButton('app-home', 'Registrar asistencia'),
    icon: renderIcon(Dashboard)
  }
  // {
  //   key: 'spacer',
  //   label: 'Sincronización',
  //   disabled: true
  // },
  // {
  //   key: 'sync-students',
  //   label: renderLink('sync-students', 'Estudiantes'),
  //   icon: renderIcon(File)
  // },
  // {
  //   key: 'sync-enroll',
  //   label: renderLink('sync-enroll', 'Matriculas'),
  //   icon: renderIcon(CalendarPlus)
  // }
]
</script>
