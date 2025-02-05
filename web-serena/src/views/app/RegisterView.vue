<template>
  <AppLayout>
    <template #side>
      <div class="h-[500px] lg:h-[0px]"></div>

      <StudentCheckList :id-register="route.params.id.toString()" />
    </template>

    <SmallSpinner v-if="isLoading" />

    <DetailContainer v-else current="Registrar">
      <p class="font-bold text-sm">Registro creado el: {{ register?.createdAt }}</p>
      <p class="font-semibold text-2xl">{{ register?.classroom.subject.name }}</p>
      <p class="text-slate-400 text-xl">{{ register?.classroom.academicPeriod.name }}</p>

      <hr class="my-2" />

      <p class="text-sm text-right">
        En la parte derecha se mostraran todos los estudiantes que se han registrado en esta clase
      </p>

      <div class="w-full h-full mt-10">
        <MainCamera :save-photo="savePhoto" infinite />
      </div>
    </DetailContainer>
  </AppLayout>
</template>

<script setup lang="ts">
import { useRoute } from 'vue-router'

import MainCamera from '@/liveness/components/MainCamera.vue'

import DetailContainer from '@/components/containers/DetailContainer.vue'
import AppLayout from '@/components/layouts/AppLayout.vue'
import StudentCheckList from '@/components/shared/StudentCheckList.vue'
import { useRegister } from '@/composable/register/useRegister'
import SmallSpinner from '@/components/shared/SmallSpinner.vue'
import { useRegisterStudent } from '@/composable/register/useRegisterStudent'
import { toRef } from 'vue'

const route = useRoute()
const id = toRef(route.params, 'id')
const { create } = useRegisterStudent(route.params.id.toString())

const { isLoading, register } = useRegister(id)

const savePhoto = async (photo: string) => {
  create(photo)
}
</script>
