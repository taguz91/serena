<template>
  <header class="bg-black text-white py-5 px-10 flex justify-between border-b-2">
    <div>
      <h1 class="font-bold text-2xl">{{ title }}</h1>
    </div>

    <div>
      <RouterLink :to="{ name: 'profile' }" class="mr-2 font-semibold underline">
        Ver Perfil
      </RouterLink>

      <NButton type="error" @click="logout">
        <template #icon>
          <NIcon><Logout /></NIcon>
        </template>
      </NButton>
    </div>
  </header>
</template>

<script setup lang="ts">
import { NButton, NIcon } from 'naive-ui'
import { Logout } from '@vicons/tabler'

import { useAuthStore } from '@/stores/user'

defineProps<{
  title: string
}>()

const userStore = useAuthStore()

const logout = () => {
  userStore.logout()
}
</script>
