import { useAuthStore } from '@/stores/user'

const request = (method: 'GET' | 'POST' | 'PUT' | 'DELETE') => {
  return async (url: string, body: Record<string, unknown> | undefined) => {
    const requestOptions: RequestInit = {
      method,
      headers: {
        ...authHeader(url),
        'Content-Type': 'application/json'
      }
    }

    if (body) {
      requestOptions.body = JSON.stringify(body)
    }

    return fetch(`http://localhost:8181/api${url}`, requestOptions).then(handleResponse)
  }
}

function authHeader(url: string): HeadersInit {
  const { user } = useAuthStore()

  const isLoggedIn = !!user?.token

  const isApiUrl = url.startsWith(import.meta.env.VITE_API_URL)
  if (isLoggedIn && isApiUrl) {
    return { Authorization: `Bearer ${user.token}` }
  } else {
    return {}
  }
}

async function handleResponse(response: Response) {
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

  return data
}

export const fetchWrapper = {
  get: request('GET'),
  post: request('POST'),
  put: request('PUT'),
  delete: request('DELETE')
}
