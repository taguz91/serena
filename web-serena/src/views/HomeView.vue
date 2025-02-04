<template>
  <SinglePageLayout>
    <div class="flex flex-col items-center justify-center h-3/5 m-auto w-full">
      <AppIcon />

      <div class="h-full w-[800px] mt-6 rounded-lg flex">
        <LoginForm />

        <div
          class="bg-black w-3/5 h-full p-8 flex flex-col items-center justify-center rounded-r-lg"
        >
          <p class="text-center text-lg text-white">
            "La educación es el arma más poderosa que puedes usar para cambiar el mundo."
          </p>

          <p class="text-center text-slate-300 mt-2">Serena - RF</p>
        </div>
      </div>
    </div>
  </SinglePageLayout>
</template>

<script setup lang="ts">
import SinglePageLayout from '@/components/layouts/SinglePageLayout.vue'
import LoginForm from '@/components/home/LoginForm.vue'
import AppIcon from '@/components/shared/AppIcon.vue'
import { useAunt } from '@/composable'
import { onMounted } from 'vue'
import { useRouter } from 'vue-router'

const { user } = useAunt()
const router = useRouter()

onMounted(() => {
  if (user.value) {
    router.push({ name: user.value.isAdmin ? 'admin-home' : 'app-home' })
  }
})
</script>
