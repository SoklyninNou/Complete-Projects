import numpy as np
import matplotlib.pyplot as plt
from scipy.integrate import odeint


# Constants and initial conditions
alpha = 0.4
c = 0.5
total_population = 500
initial_N_n = 100
initial_N_h = 200
initial_J_w = 20
initial_J_n = 50
P = 130  # Constant population of Potential Batman


# Time span
t = np.linspace(0, 2.5, 1000)


# Differential equations
def equations(y, t, alpha, c, P):
   N_n, N_h, J_w, J_n = y


   P_n = alpha ** t
   F_w = -1 * P_n + 1
   P_r = 1 - (1/500 * J_n - F_w - 1 / 2 * P_n)


   dN_h_dt = c * (N_n * J_w - P_r * N_h * P - N_h * J_w)
   dN_n_dt = c * (F_w * J_n * P + P_r * N_h * P - N_n * J_w + 1 / 2 * P_n * J_n * P)
   dJ_n_dt = c * (1 / 2 * P_n * J_w * P - F_w * J_n * P - 1 / 2 * P_n * J_n * P + N_h * J_w)
   dJ_w_dt = c * (1 / 2 * P_n * J_n * P - 1 / 2 * P_n * J_w * P)


   populations = np.array([N_n + dN_n_dt, N_h + dN_h_dt, J_w + dJ_w_dt, J_n + dJ_n_dt])
   populations = np.maximum(populations, 0)
   total = np.sum(populations) + P
   populations = total_population * populations / total


   return populations[0] - N_n, populations[1] - N_h, populations[2] - J_w, populations[3] - J_n


# Initial conditions
initial_conditions = [initial_N_n, initial_N_h, initial_J_w, initial_J_n]


# Solving the system of differential equations
solution = odeint(equations, initial_conditions, t, args=(alpha, c, P))


# Extracting solutions
N_n, N_h, J_w, J_n = solution.T


# Happiness calculation
happiness_changes = (
   (N_n[1:] - N_n[:-1]) * 1.5 +  # N_n to N_h
   (N_h[1:] - N_h[:-1]) * (-1) +  # N_h to N_n
   (J_n[1:] - J_n[:-1]) * 1 +  # J_n to J_w
   (J_w[1:] - J_w[:-1]) * 1  # J_w to J_n
)
happiness = np.cumsum(np.insert(happiness_changes, 0, 0))

# Calculate stability
stability = np.cumsum(happiness_changes)

# Population dynamics plot
plt.figure(figsize=(12, 6))
plt.plot(t, N_n, label='Normal People (N_n)')
plt.plot(t, N_h, label='Hurted People (N_h)')
plt.plot(t, J_w, label='Worse Joker (J_w)')
plt.plot(t, J_n, label='Normal Joker (J_n)')
plt.plot(t, [P]*len(t), label='Potential Batman (P)')
plt.title('Population Dynamics Over Time')
plt.xlabel('Time')
plt.ylabel('Population')
plt.legend()
plt.grid(True)
plt.show()
# Happiness plot
plt.figure(figsize=(12, 6))
plt.plot(t, happiness, label='Happiness')
plt.title('Happiness Over Time')
plt.xlabel('Time')
plt.ylabel('Happiness')
plt.legend()
plt.grid(True)
plt.show()
# Stability plot
plt.figure(figsize=(12, 6))
plt.plot(t[1:], stability, label='Stability')
plt.title('Stability Over Time')
plt.xlabel('Time')
plt.ylabel('Stability')
plt.legend()
plt.grid(True)
plt.show()

