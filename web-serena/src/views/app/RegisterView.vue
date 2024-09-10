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

      <div class="flex w-full h-full justify-center items-center mt-10">
        <div class="bg-white rounded-lg w-96 h-96"></div>
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

const route = useRoute()

const { isLoading, register } = useRegister(route.params.id.toString())
</script>
