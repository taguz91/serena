<template>
  <SinglePageLayout>
    <div class="flex flex-col items-center justify-center h-3/5 m-auto w-full">
      <AppIcon />

      <div class="h-full w-[600px] mt-6 rounded-lg flex items-center justify-center">
        <RegisterForm />
      </div>
    </div>
  </SinglePageLayout>
</template>

<script setup lang="ts">
import RegisterForm from '@/components/home/RegisterForm.vue'
import SinglePageLayout from '@/components/layouts/SinglePageLayout.vue'
import AppIcon from '@/components/shared/AppIcon.vue'
</script>
