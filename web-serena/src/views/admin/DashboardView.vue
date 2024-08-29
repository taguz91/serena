<template>
  <NPageHeader
    title="Últimos registros creados"
    subtitle="Se mostraran los últimos 10 registros creados, para revisarlos"
  >
    <NDataTable :columns="columns" :data="data" pagination bordered />
  </NPageHeader>
</template>

<script setup lang="ts">
import { NDataTable, NPageHeader, type DataTableColumns } from 'naive-ui'
import { onMounted, ref } from 'vue'

interface Register {
  date: string
  subject: string
  teacher: string
}

const data = ref<Register[]>([])

const columns: DataTableColumns<Register> = [
  {
    title: 'Subject',
    key: 'subject'
  },
  {
    title: 'Teacher',
    key: 'teacher'
  },
  {
    title: 'Date',
    key: 'date'
  }
]

onMounted(() => {
  setTimeout(() => {
    data.value = [
      {
        date: '2021-10-10',
        subject: 'Math',
        teacher: 'John Doe'
      },
      {
        date: '2021-10-11',
        subject: 'Science',
        teacher: 'Jane Doe'
      },
      {
        date: '2021-10-12',
        subject: 'History',
        teacher: 'John Doe'
      },
      {
        date: '2021-10-13',
        subject: 'Math',
        teacher: 'Jane Doe'
      },
      {
        date: '2021-10-14',
        subject: 'Science',
        teacher: 'John Doe'
      },
      {
        date: '2021-10-15',
        subject: 'History',
        teacher: 'Jane Doe'
      },
      {
        date: '2021-10-16',
        subject: 'Math',
        teacher: 'John Doe'
      },
      {
        date: '2021-10-17',
        subject: 'Science',
        teacher: 'Jane Doe'
      },
      {
        date: '2021-10-18',
        subject: 'History',
        teacher: 'John Doe'
      },
      {
        date: '2021-10-19',
        subject: 'Math',
        teacher: 'Jane Doe'
      }
    ]
  }, 1500)
})
</script>
