<template>
  <aside class="fixed top-0 left-0 w-24 h-screen bg-black p-5">
    <div class="flex flex-col h-full justify-center items-center">
      <div>
        <RouterLink :to="{ name: 'credits' }">
          <img src="/images/logo-bg-black.png" alt="logo" class="w-28 h-11" />
        </RouterLink>
      </div>

      <nav class="flex-1 flex flex-col justify-between">
        <ul class="my-auto space-y-4">
          <li>
            <RouterLink
              :to="{
                name: 'app-home'
              }"
              class="block py-2 px-3 text-white rounded-md hover:text-slate-100"
            >
              <NIcon size="26">
                <Home />
              </NIcon>
            </RouterLink>
          </li>

          <li>
            <RouterLink
              :to="{
                name: 'profile'
              }"
              class="block py-2 px-3 text-white rounded-md hover:text-slate-100"
            >
              <NIcon size="26">
                <User />
              </NIcon>
            </RouterLink>
          </li>

          <li v-if="user?.isAdmin">
            <RouterLink
              :to="{
                name: 'admin-home'
              }"
              class="block py-2 px-3 text-white rounded-md hover:text-slate-100"
            >
              <NIcon size="26">
                <Settings />
              </NIcon>
            </RouterLink>
          </li>
        </ul>
      </nav>

      <NButton class="rounded-md" type="error" @click="logout">
        <template #icon>
          <NIcon><Logout /></NIcon>
        </template>
      </NButton>
    </div>
  </aside>
</template>

<script setup lang="ts">
import { useAunt } from '@/composable'
import { useAuthStore } from '@/stores/user'
import { Home, Logout, Settings, User } from '@vicons/tabler'
import { NButton, NIcon } from 'naive-ui'

const userStore = useAuthStore()
const { user } = useAunt()

const logout = () => {
  userStore.logout()
}
</script>
