<template>
  <AppLayout>
    <template #side>
      <StudentList />
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
          <NButton type="info" @click="goClassroom(classroom.id)">
            <template #icon>
              <NIcon><LayoutBoard /></NIcon>
            </template>
          </NButton>
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

import { NButton, NCard, NIcon } from 'naive-ui'
import { User, LayoutBoard } from '@vicons/tabler'

import AppLayout from '@/components/layouts/AppLayout.vue'
import StudentList from '@/components/shared/StudentList.vue'
import { useCurrentClassrooms } from '@/composable/classrooms/useCurrentClassrooms'
import { userCreateRegister } from '@/composable/register/useRegister'
import type { Classroom } from '@/interfaces'

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

const createRegister = (classroom: Classroom) => {
  create({
    idClassroom: classroom.id,
    status: 'open'
  })
}
</script>
