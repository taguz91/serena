<template>
  <h2 class="mb-2 font-bold text-xl p-4">Estudiantes</h2>
  <NMenu mode="vertical" :options="menuOptions" />
</template>

<script setup lang="ts">
import { computed, h, type Component } from 'vue'
import { RouterLink } from 'vue-router'

import { User } from '@vicons/tabler'

import { NIcon, NMenu, type MenuOption } from 'naive-ui'
import { useStudentsTeacher } from '@/composable/students/useStudents'

const { students } = useStudentsTeacher()

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

const menuOptions = computed(() => {
  return students.value.map((student): MenuOption => {
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
