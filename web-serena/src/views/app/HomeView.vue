<template>
  <AppLayout>
    <div class="flex">
      <div class="p-5 grid grid-cols-1 gap-4 flex-1">
        <div
          class="hover:bg-slate-100 p-5 border border-black shadow-lg rounded-xl flex cursor-pointer"
          v-for="classroom in classrooms"
          :key="classroom.id"
          @click.stop="goHistory(classroom.id)"
        >
          <div class="flex-1 flex flex-col mr-4">
            <h1 class="text-2xl font-bold pb-2 border-b">
              {{ classroom.subject.name }}
            </h1>

            <p class="w-full text-xl mt-4">{{ classroom.academicPeriod.name }}</p>
          </div>

          <div class="flex flex-col gap-y-2">
            <OutlineButton
              @click="createRegister(classroom)"
              label="Registrar"
              :loading="idClassroomCreating == classroom.id"
            />

            <OutlineButton @click="createInscription(classroom)" label="Crear inscripción" />

            <OutlineButton @click="goClassroom(classroom.id)" label="Ver reporte" />
          </div>
        </div>
      </div>

      <div class="flex lg:w-[100px] xl:w-[500px]"></div>
    </div>
  </AppLayout>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'

import AppLayout from '@/components/layouts/AppLayout.vue'
import { useCurrentClassrooms } from '@/composable/classrooms/useCurrentClassrooms'
import { userCreateRegister } from '@/composable/register/useRegister'
import type { Classroom } from '@/interfaces'
import OutlineButton from '@/components/basic/OutlineButton.vue'

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
</script>
