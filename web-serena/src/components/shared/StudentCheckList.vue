<template>
  <h2 class="mb-2 font-bold text-xl p-4">Estudiantes: #{{ numStudents }}</h2>
  <NMenu mode="vertical" :options="menuOptions" />
</template>

<script setup lang="ts">
import { computed, h, ref, watch, type Component } from 'vue'

import { Check, CheckupList } from '@vicons/tabler'
import { NIcon, NMenu, type MenuOption } from 'naive-ui'

import { useRegisterStudents } from '@/composable/register/userRegisterStudents'

interface Props {
  idRegister: string
}

const props = defineProps<Props>()

const menuOptions = ref<MenuOption[]>([])
const { students } = useRegisterStudents(props.idRegister)

const renderIcon = (icon: Component) => {
  return () => h(NIcon, null, { default: () => h(icon) })
}

const numStudents = computed(() => students.value.length)

watch(students, () => {
  menuOptions.value = students.value.map((student): MenuOption => {
    return {
      key: student.id,
      label: () => {
        return h('div', { class: 'text-left' }, [
          h('p', student.student.name.length > 0 ? student.student.name : 'Sin nombre'),
          h('p', { class: 'ml-2 text-sm text-gray-500' }, student.student.createdAt)
        ])
      },
      icon: renderIcon(parseInt(student.id) > 4 ? CheckupList : Check),
      props: {
        class: 'hover:bg-blue-200'
      }
    } as MenuOption
  })
})
</script>
