import asyncio

class InsufficientFundsError(Exception):
    """Wyjątek podnoszony, gdy saldo jest niewystarczające."""
    pass

class Account:
    def __init__(self, account_number: str, owner: str, initial_balance: float = 0.0):
        self.account_number = account_number
        self.owner = owner
        self.balance = initial_balance

    def deposit(self, amount: float):
        if amount <= 0:
            raise ValueError("Wpłata musi być większa od 0.")
        self.balance += amount

    def withdraw(self, amount: float):
        if amount <= 0:
            raise ValueError("Kwota wypłaty musi być większa od 0.")
        if amount > self.balance:
            raise InsufficientFundsError("Niewystarczające środki na koncie.")
        self.balance -= amount

    async def transfer(self, to_account: "Account", amount: float):
        if amount <= 0:
            raise ValueError("Kwota transferu musi być większa od 0.")
        if amount > self.balance:
            raise InsufficientFundsError("Niewystarczające środki na koncie.")
        await asyncio.sleep(0.1)  # Symulacja operacji asynchronicznej
        self.withdraw(amount)
        to_account.deposit(amount)


class Bank:
    def __init__(self):
        self.accounts = {}

    def create_account(self, account_number: str, owner: str, initial_balance: float = 0.0):
        if account_number in self.accounts:
            raise ValueError("Konto o tym numerze już istnieje.")
        account = Account(account_number, owner, initial_balance)
        self.accounts[account_number] = account

    def get_account(self, account_number: str) -> Account:
        if account_number not in self.accounts:
            raise ValueError("Nie znaleziono konta o podanym numerze.")
        return self.accounts[account_number]

    async def process_transaction(self, transaction_func):
        """Procesuje transakcje asynchroniczne."""
        await transaction_func()
