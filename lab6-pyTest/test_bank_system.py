import pytest
import pytest_asyncio
from unittest.mock import AsyncMock
from bank_system import Account, Bank, InsufficientFundsError


@pytest.fixture
def sample_account():
    return Account("123456", "Jan Kowalski", 1000.0)


@pytest.fixture
def bank():
    return Bank()


# Testy dla klasy Account
def test_deposit(sample_account):
    sample_account.deposit(500)
    assert sample_account.balance == 1500


def test_deposit_negative_value(sample_account):
    with pytest.raises(ValueError, match="Wpłata musi być większa od 0."):
        sample_account.deposit(-100)


def test_withdraw(sample_account):
    sample_account.withdraw(500)
    assert sample_account.balance == 500


def test_withdraw_insufficient_funds(sample_account):
    with pytest.raises(InsufficientFundsError, match="Niewystarczające środki na koncie."):
        sample_account.withdraw(1500)


@pytest.mark.asyncio
async def test_transfer(sample_account):
    target_account = Account("654321", "Anna Nowak", 500)
    await sample_account.transfer(target_account, 200)

    assert sample_account.balance == 800
    assert target_account.balance == 700


@pytest.mark.asyncio
async def test_transfer_insufficient_funds(sample_account):
    target_account = Account("654321", "Anna Nowak", 500)
    with pytest.raises(InsufficientFundsError):
        await sample_account.transfer(target_account, 2000)


# Testy dla klasy Bank
def test_create_account(bank):
    bank.create_account("123456", "Jan Kowalski", 1000.0)
    assert bank.get_account("123456").balance == 1000.0


def test_create_duplicate_account(bank):
    bank.create_account("123456", "Jan Kowalski", 1000.0)
    with pytest.raises(ValueError, match="Konto o tym numerze już istnieje."):
        bank.create_account("123456", "Anna Nowak", 500.0)


def test_get_account(bank):
    bank.create_account("123456", "Jan Kowalski", 1000.0)
    account = bank.get_account("123456")
    assert account.owner == "Jan Kowalski"


def test_get_nonexistent_account(bank):
    with pytest.raises(ValueError, match="Nie znaleziono konta o podanym numerze."):
        bank.get_account("654321")


@pytest.mark.asyncio
async def test_process_transaction(bank):
    source_account = Account("123456", "Jan Kowalski", 1000)
    target_account = Account("654321", "Anna Nowak", 500)

    async def mock_transaction():
        await source_account.transfer(target_account, 200)

    bank.accounts["123456"] = source_account
    bank.accounts["654321"] = target_account

    await bank.process_transaction(mock_transaction)

    assert source_account.balance == 800
    assert target_account.balance == 700


# Testowanie mockowania
@pytest.mark.asyncio
async def test_mock_external_system():
    mock_external_system = AsyncMock(return_value="Approved")
    result = await mock_external_system()
    assert result == "Approved"
    mock_external_system.assert_called_once()
