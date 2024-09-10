<template>
  <template v-if="!loaded">
    <main class="h-screen w-screen flex justify-center items-center">
      <p class="text-9xl text-slate-500 text-center">...</p>
    </main>
  </template>

  <template v-else>
    <template v-if="isAdmin">
      <AdminLayout />
    </template>

    <RouterView v-else />
  </template>
</template>

<script setup lang="ts">
import { computed, onMounted, ref } from 'vue'

import { RouterView, useRoute, useRouter } from 'vue-router'
import { useLoadingBar } from 'naive-ui'

import { useAuthStore } from '@/stores/user'
import AdminLayout from '@/components/layouts/AdminLayout.vue'

const route = useRoute()
const router = useRouter()
const isAdmin = computed(() => route.path.startsWith('/admin'))

const loaded = ref(false)
const userStore = useAuthStore()
const loading = useLoadingBar()

onMounted(async () => {
  // load the data from current user
  loading.start()
  await userStore.init()
  loading.finish()
  loaded.value = true

  // no debe redireccionar si ya esta en la pagina
  // if (!teacher) return
  // if (teacher.email.includes('admin@')) {
  //   router.push({ name: 'admin-home' })
  //   return
  // }
  // if (!teacher.isActive) {
  //   router.push({ name: 'welcome' })
  // }
  // if (teacher.isActive) {
  //   router.push({ name: 'app-home' })
  // }
})
</script>
