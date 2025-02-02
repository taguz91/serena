<template>
  <StudentList :students="students" />
</template>

<script setup lang="ts">
import { useStudentsTeacher } from '@/composable/students/useStudents'
import StudentList from './StudentList.vue'

const { students } = useStudentsTeacher()
</script>
