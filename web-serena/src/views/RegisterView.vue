<template>
  <SinglePageLayout>
    <div class="flex flex-col items-center justify-center h-3/5 m-auto w-full">
      <AppIcon />

      <div class="h-full w-[800px] mt-6 rounded-lg flex">
        <RegisterForm />

        <div
          class="bg-blue-100 opacity-40 w-3/5 h-full p-8 flex flex-col items-center justify-center rounded-r-lg"
        >
          <p class="text-center text-lg">
            "La educación es el arma más poderosa que puedes usar para cambiar el mundo."
          </p>

          <p class="text-center text-slate-700 mt-2">Serena - RF</p>
        </div>
      </div>
    </div>
  </SinglePageLayout>
</template>

<script setup lang="ts">
import RegisterForm from '@/components/home/RegisterForm.vue'
import SinglePageLayout from '@/components/layouts/SinglePageLayout.vue'
import AppIcon from '@/components/shared/AppIcon.vue'
</script>
