<template>
  <AppLayout>
    <template #side>
      <StudentCheckList :id-register="route.params.id.toString()" />
    </template>

    <SmallSpinner v-if="isLoading" />

    <DetailContainer v-else current="Registrar">
      <NInput :value="url" disabled />

      <hr class="my-2" />

      <p class="font-bold text-lg">{{ register?.createdAt }}</p>
    </DetailContainer>
  </AppLayout>
</template>

<script setup lang="ts">
import { useRoute, useRouter } from 'vue-router'

import DetailContainer from '@/components/containers/DetailContainer.vue'
import AppLayout from '@/components/layouts/AppLayout.vue'
import StudentCheckList from '@/components/shared/StudentCheckList.vue'
import { useRegister } from '@/composable/register/useRegister'
import SmallSpinner from '@/components/shared/SmallSpinner.vue'
import { NInput } from 'naive-ui'
import { computed } from 'vue'

const route = useRoute()
const router = useRouter()

const { isLoading, register } = useRegister(route.params.id.toString())

const url = computed(() => {
  const href = router.resolve({
    name: register.value?.status === 'inscription' ? 'public-register-check' : 'classroom-register',
    params: {
      id: route.params.id.toString()
    }
  }).href

  return `${window.location.origin}${href}`
})
</script>
