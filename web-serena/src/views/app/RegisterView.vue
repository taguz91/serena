<template>
  <AppLayout>
    <template #side>
      <StudentCheckList />
    </template>

    <SmallSpinner v-if="isLoading" />

    <DetailContainer v-else current="Registrar">
      <p class="font-bold text-lg">{{ register?.createdAt }}</p>
      <p class="font-semibold">{{ register?.classroom.subject.name }}</p>
      <p class="text-sm text-slate-400">{{ register?.classroom.academicPeriod.name }}</p>

      <hr class="my-2" />

      <div class="w-full h-full mt-10">
        <MainCamera :save-photo="savePhoto" />

        <!-- <div class="bg-white rounded-lg w-96 h-96"></div> -->
      </div>
    </DetailContainer>
  </AppLayout>
</template>

<script setup lang="ts">
import { useRoute } from 'vue-router'

import DetailContainer from '@/components/containers/DetailContainer.vue'
import AppLayout from '@/components/layouts/AppLayout.vue'
import StudentCheckList from '@/components/shared/StudentCheckList.vue'
import { useRegister } from '@/composable/register/useRegister'
import SmallSpinner from '@/components/shared/SmallSpinner.vue'
import MainCamera from '@/liveness/components/MainCamera.vue'
import { useRegisterStudent } from '@/composable/register/useRegisterStudent'

const route = useRoute()
const { create } = useRegisterStudent(route.params.id.toString())

const { isLoading, register } = useRegister(route.params.id.toString())

const savePhoto = async (photo: string) => {
  create(photo)
}
</script>
