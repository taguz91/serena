<template>
  <AppLayout>
    <div class="flex">
      <div class="py-7 px-10 grid grid-cols-1 gap-4 flex-1">
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

      <NModal
        v-model:show="show"
        :mask-closable="false"
        preset="dialog"
        class="w-[500px]"
        title="Crear un registro"
        :positive-text="'Crear'"
        negative-text="Cancelar"
        @positive-click="onPositiveClick"
        @negative-click="onNegativeClick"
      >
        <template #icon>
          <NIcon class="text-slate-800">
            <Clipboard />
          </NIcon>
        </template>

        <NForm ref="formRef" class="mt-6" :model="model" :rules="rules">
          <NFormItem label="Tema de la clase" required>
            <NInput v-model:value="model.topic" placeholder="Tema" />
          </NFormItem>
        </NForm>
      </NModal>

      <div class="flex lg:w-[100px] xl:w-[500px]"></div>
    </div>
  </AppLayout>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'
import { ref } from 'vue'

import { NForm, NFormItem, NIcon, NInput, NModal, type FormInst, type FormRules } from 'naive-ui'
import { Clipboard } from '@vicons/tabler'

import AppLayout from '@/components/layouts/AppLayout.vue'
import { useCurrentClassrooms } from '@/composable/classrooms/useCurrentClassrooms'
import { userCreateRegister } from '@/composable/register/useRegister'
import OutlineButton from '@/components/basic/OutlineButton.vue'
import type { Classroom, TopicCache } from '@/interfaces'

const router = useRouter()
const { classrooms } = useCurrentClassrooms()
const { idClassroomCreating, create } = userCreateRegister()

const currentClassroom = ref<Classroom | null>(null)
const formRef = ref<FormInst | null>(null)
const show = ref(false)

const model = ref({
  topic: ''
})

const rules: FormRules = {
  topic: {
    required: true,
    trigger: ['blur'],
    message: 'El topic es requerido'
  }
}

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

const onPositiveClick = () => {
  if (!currentClassroom.value) {
    return
  }

  create({
    idClassroom: currentClassroom.value.id,
    status: 'open',
    topic: model.value.topic
  })

  const values: TopicCache[] = JSON.parse(localStorage.getItem('lastTopics') ?? '[]')
  values.push({
    idClassroom: currentClassroom.value.id,
    topic: model.value.topic,
    time: new Date().getTime()
  })
  localStorage.setItem('lastTopics', JSON.stringify(values))

  show.value = false
}

const onNegativeClick = () => {
  show.value = false
}

const createRegister = (classroom: Classroom) => {
  currentClassroom.value = classroom
  show.value = true

  // preload last topic from 10 minutes
  const values: TopicCache[] = JSON.parse(localStorage.getItem('lastTopics') ?? '[]')

  const index = values.findIndex((value: TopicCache) => value.idClassroom === classroom.id)

  if (index !== -1) {
    const actualTopic = values[index]
    const time = new Date().getTime() - actualTopic.time

    // check if pass 10 minutes
    if (time > 10 * 60 * 1000) {
      model.value.topic = ''
      values.splice(index, 1)

      localStorage.setItem('lastTopics', JSON.stringify(values))
    } else {
      model.value.topic = actualTopic.topic
    }
  }
}

const createInscription = (classroom: Classroom) => {
  create({
    idClassroom: classroom.id,
    status: 'inscription',
    topic: 'Inscripción'
  })
}
</script>
