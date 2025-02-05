<template>
  <PublicLayout>
    <SmallSpinner v-if="isLoading" />

    <div v-else class="my-10">
      <p class="font-bold text-lg">{{ register?.createdAt }}</p>
      <p class="font-semibold">{{ register?.classroom.subject.name }}</p>
      <p class="text-sm text-slate-400">{{ register?.classroom.academicPeriod.name }}</p>

      <hr class="my-2" />

      <div v-if="created" class="text-center mt-2">
        <p class="font-semibold">Completamos correctamente la inscripción</p>

        <p class="text-xs">
          {{ reference }}
        </p>

        <p class="text-slate-300">Puedes cerrar esta ventana</p>
      </div>

      <div v-else class="w-full mt-10">
        <MainCamera :save-photo="savePhoto" :infinite="false" />
      </div>
    </div>
  </PublicLayout>
</template>

<script setup lang="ts">
import { useRoute } from 'vue-router'

import MainCamera from '@/liveness/components/MainCamera.vue'

import { useRegister } from '@/composable/register/useRegister'
import SmallSpinner from '@/components/shared/SmallSpinner.vue'
import PublicLayout from '@/components/layouts/PublicLayout.vue'
import { useRegisterStudentDuplicate } from '@/composable/register/useRegisterStudentDuplicate'
import type { RegisterStudent } from '@/interfaces'
import { ref, toRef } from 'vue'

const route = useRoute()
const created = ref(false)
const reference = ref('')
const id = toRef(route.params, 'id')

const { update } = useRegisterStudentDuplicate(
  route.params.id.toString(),
  (register: RegisterStudent) => {
    created.value = true

    reference.value = register.id
  }
)

const { isLoading, register } = useRegister(id)

const savePhoto = async (photo: string) => {
  update(route.params.idStudent.toString(), photo)
}
</script>
