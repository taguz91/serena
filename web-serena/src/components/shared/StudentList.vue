<template>
  <h2 class="mb-2 font-bold text-xl p-4">Estudiantes</h2>
  <NMenu mode="vertical" :options="menuOptions" />
</template>

<script setup lang="ts">
import { computed, h, type Component } from 'vue'
import { RouterLink } from 'vue-router'

import { User } from '@vicons/tabler'

import { NIcon, NMenu, type MenuOption } from 'naive-ui'

const renderIcon = (icon: Component) => {
  return () => h(NIcon, null, { default: () => h(icon) })
}

const renderLink = (to: string, label: string, id: string) => {
  return () =>
    h(
      RouterLink,
      {
        to: {
          name: to,
          params: {
            id
          }
        }
      },
      { default: () => label }
    )
}

const students = [
  {
    id: '1',
    name: 'John Doe',
    age: 20
  },
  {
    id: '2',
    name: 'Jane Doe',
    age: 21
  },
  {
    id: '3',
    name: 'John Smith',
    age: 22
  },
  {
    id: '4',
    name: 'Jane Smith',
    age: 23
  },
  {
    id: '5',
    name: 'John Johnson',
    age: 24
  },
  {
    id: '6',
    name: 'Jane Johnson',
    age: 25
  },
  {
    id: '7',
    name: 'John Brown',
    age: 26
  },
  {
    id: '8',
    name: 'Jane Brown',
    age: 27
  },
  {
    id: '9',
    name: 'John White',
    age: 28
  },
  {
    id: '10',
    name: 'Jane White',
    age: 29
  }
]

const menuOptions = computed(() => {
  return students.map((student): MenuOption => {
    return {
      key: student.id,
      label: renderLink('student-report', student.name, student.id),
      icon: renderIcon(User),
      props: {
        class: 'hover:bg-blue-200'
      }
    } as MenuOption
  })
})
</script>
