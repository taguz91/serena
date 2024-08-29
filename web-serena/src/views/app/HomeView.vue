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
        :title="classroom.subject"
        bordered
      >
        <div class="flex">
          <p class="w-full">{{ classroom.academicPeriod }}</p>
          <NButton type="info" @click="goClassroom(classroom.id)">
            <template #icon>
              <NIcon><LayoutBoard /></NIcon>
            </template>
          </NButton>
        </div>

        <div class="flex justify-end">
          <NButton type="primary" class="mt-4 ml-auto" @click="createRegister">
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

import AppLayout from '@/components/layouts/AppLayout.vue'

import { NButton, NCard, NIcon } from 'naive-ui'
import { User, LayoutBoard } from '@vicons/tabler'
import StudentList from '@/components/shared/StudentList.vue'

const router = useRouter()

const goClassroom = (id: string) => {
  router.push({
    name: 'classroom-report',
    params: {
      id
    }
  })
}

const createRegister = () => {
  router.push({
    name: 'classroom-register',
    params: {
      id: '1'
    }
  })
}

interface Classroom {
  id: string
  subject: string
  academicPeriod: string
}

const classrooms: Classroom[] = [
  {
    id: '1',
    subject: 'Mathematics',
    academicPeriod: '2021-2022'
  },
  {
    id: '2',
    subject: 'Science',
    academicPeriod: '2021-2022'
  },
  {
    id: '3',
    subject: 'English',
    academicPeriod: '2021-2022'
  },
  {
    id: '4',
    subject: 'History',
    academicPeriod: '2021-2022'
  },
  {
    id: '5',
    subject: 'Geography',
    academicPeriod: '2021-2022'
  },
  {
    id: '6',
    subject: 'Physical Education',
    academicPeriod: '2021-2022'
  },
  {
    id: '7',
    subject: 'Music',
    academicPeriod: '2021-2022'
  },
  {
    id: '8',
    subject: 'Art',
    academicPeriod: '2021-2022'
  },
  {
    id: '9',
    subject: 'Computer Science',
    academicPeriod: '2021-2022'
  },
  {
    id: '10',
    subject: 'Health',
    academicPeriod: '2021-2022'
  }
]
</script>
