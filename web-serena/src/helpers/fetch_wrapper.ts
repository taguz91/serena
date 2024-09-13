import { useAuthStore } from '@/stores/user'

const request = (method: 'GET' | 'POST' | 'PUT' | 'DELETE') => {
  return async <T, R>(url: string, data: T | undefined = undefined) => {
    const requestOptions: RequestInit = {
      method,
      headers: {
        ...authHeader(),
        'Content-Type': 'application/json'
      }
    }

    if (data) {
      requestOptions.body = JSON.stringify(data)
    }

    return fetch(`http://localhost:8181/api${url}`, requestOptions).then((response) =>
      handleResponse<R>(response)
    )
  }
}

const requestFormData = (method: 'POST') => {
  return async <R>(url: string, data: FormData) => {
    const requestOptions: RequestInit = {
      method,
      headers: {
        ...authHeader(),
        'Content-Type': 'multipart/form-data'
      }
    }

    requestOptions.body = data

    return fetch(`http://localhost:8181/api${url}`, requestOptions).then((response) =>
      handleResponseWithoutLogout<R>(response)
    )
  }
}

function authHeader(): HeadersInit {
  const { user } = useAuthStore()

  const isLoggedIn = !!user?.token

  if (isLoggedIn) {
    return { Authorization: `Bearer ${user.token}` }
  } else {
    return {}
  }
}

async function handleResponse<R>(response: Response) {
  const isJson = response.headers?.get('content-type')?.includes('application/json')
  const data = isJson ? await response.json() : null

  // check for error response
  if (!response.ok) {
    const { user, logout } = useAuthStore()
    if ([401, 403].includes(response.status) && user) {
      // auto logout if 401 Unauthorized or 403 Forbidden response returned from api
      logout()
    }

    // parse the response to custom exceptions

    // get error message from body or default to response status
    const error = (data && data.message) || response.status
    return Promise.reject(error)
  }

  return data as R
}

async function handleResponseWithoutLogout<R>(response: Response) {
  const isJson = response.headers?.get('content-type')?.includes('application/json')
  const data = isJson ? await response.json() : null

  // check for error response
  if (!response.ok) {
    // get error message from body or default to response status
    const error = (data && data.message) || response.status
    return Promise.reject(error)
  }

  return data as R
}

export const fetchWrapper = {
  get: request('GET'),
  post: request('POST'),
  postFile: requestFormData('POST'),
  put: request('PUT'),
  delete: request('DELETE')
}
