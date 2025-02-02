<template>
  <AppLayout>
    <template #side>
      <StudentTeacherList />
    </template>

    <div class="p-5 grid grid-cols-2 gap-4">
      <NCard
        class="hover:bg-blue-100"
        v-for="classroom in classrooms"
        :key="classroom.id"
        :title="classroom.subject.name"
        bordered
      >
        <div class="flex">
          <p class="w-full">{{ classroom.academicPeriod.name }}</p>

          <NDropdown
            :options="options"
            @select="(key: string | number) => handleSelect(classroom, key)"
          >
            <NButton type="tertiary">
              <template #icon>
                <NIcon><DotsVertical /></NIcon>
              </template>
            </NButton>
          </NDropdown>
        </div>

        <div class="flex justify-end">
          <NButton
            type="primary"
            class="mt-4 ml-auto"
            @click="createRegister(classroom)"
            :loading="idClassroomCreating === classroom.id"
          >
            <template #icon>
              <NIcon><User /></NIcon>
            </template>
            Registrar
          </NButton>
        </div>
      </NCard>
    </div>
  </AppLayout>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'

import { NButton, NCard, NDropdown, NIcon } from 'naive-ui'
import { User, LayoutBoard, DotsVertical, Clipboard } from '@vicons/tabler'

import AppLayout from '@/components/layouts/AppLayout.vue'
import { useCurrentClassrooms } from '@/composable/classrooms/useCurrentClassrooms'
import { userCreateRegister } from '@/composable/register/useRegister'
import type { Classroom } from '@/interfaces'
import type { DropdownMixedOption } from 'naive-ui/es/dropdown/src/interface'
import { h, type Component } from 'vue'
import StudentTeacherList from '@/components/shared/StudentTeacherList.vue'

const router = useRouter()
const { classrooms } = useCurrentClassrooms()
const { idClassroomCreating, create } = userCreateRegister()

const goClassroom = (id: string) => {
  router.push({
    name: 'classroom-report',
    params: {
      id
    }
  })
}

const goHistory = (id: string) => {
  router.push({
    name: 'history-classroom-registers',
    params: {
      id
    }
  })
}

const createRegister = (classroom: Classroom) => {
  create({
    idClassroom: classroom.id,
    status: 'open'
  })
}

const createInscription = (classroom: Classroom) => {
  create({
    idClassroom: classroom.id,
    status: 'inscription'
  })
}

const renderIcon = (icon: Component) => {
  return () => {
    return h(NIcon, null, {
      default: () => h(icon)
    })
  }
}

const handleSelect = (classroom: Classroom, key: string | number) => {
  switch (key) {
    case 'report':
      goClassroom(classroom.id)
      break
    case 'inscription':
      createInscription(classroom)
      break
    case 'history':
      goHistory(classroom.id)
      break
  }
}

const options: DropdownMixedOption[] = [
  {
    label: 'Ver reporte',
    key: 'report',
    icon: renderIcon(LayoutBoard)
  },
  {
    label: 'Crear inscripción',
    key: 'inscription',
    icon: renderIcon(Clipboard)
  },
  {
    label: 'Ver historial',
    key: 'history',
    icon: renderIcon(Clipboard)
  }
]
</script>
